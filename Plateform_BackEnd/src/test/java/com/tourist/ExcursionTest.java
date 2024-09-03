//package com.tourist;
//
//import com.tourist.dto.ExcursionDTO;
//import com.tourist.model.Excursion;
//import com.tourist.repository.ExcursionRepository;
//import com.tourist.service.ExcursionService;
//import jakarta.persistence.EntityNotFoundException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//class ExcursionTest {
//
//    @Mock
//    private ExcursionRepository excursionRepository;
//
//    @InjectMocks
//    private ExcursionService excursionService;
//
//    private static final String IMAGE_DIRECTORY = "src/main/resources/img/";
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        File directory = new File(IMAGE_DIRECTORY);
//        if (!directory.exists()) {
//            directory.mkdirs();
//        }
//    }
//
//
//
//
//
//    @Test
//    void testGetAllExcursions() {
//        when(excursionRepository.findAll()).thenReturn(List.of(new Excursion()));
//
//        List<Excursion> excursions = excursionService.getAllExcursions();
//
//        assertNotNull(excursions);
//        assertFalse(excursions.isEmpty());
//    }
//
//    @Test
//    void testSearchExcursions() {
//        LocalDateTime date = LocalDateTime.now();
//        String location = "Location";
//
//        when(excursionRepository.findByDateTimeAndLocation(date, location)).thenReturn(List.of(new Excursion()));
//
//        List<Excursion> excursions = excursionService.searchExcursions(date, location);
//
//        assertNotNull(excursions);
//        assertFalse(excursions.isEmpty());
//    }
//
//    @Test
//    void testFilterExcursionsByRating() {
//        Long minRating = 1L;
//        Long maxRating = 5L;
//
//        when(excursionRepository.findByRatingBetween(minRating, maxRating)).thenReturn(List.of(new Excursion()));
//
//        List<Excursion> excursions = excursionService.filterExcursionsByRating(minRating, maxRating);
//
//        assertNotNull(excursions);
//        assertFalse(excursions.isEmpty());
//    }
//
//    @Test
//    void testGetExcursionByIdSuccess() {
//        Excursion excursion = new Excursion();
//        when(excursionRepository.findById(1L)).thenReturn(Optional.of(excursion));
//
//        Excursion foundExcursion = excursionService.getExcursionById(1L);
//
//        assertNotNull(foundExcursion);
//    }
//
//    @Test
//    void testGetExcursionByIdNotFound() {
//        when(excursionRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(EntityNotFoundException.class, () -> excursionService.getExcursionById(1L));
//    }
//
//    @Test
//    void testUpdateExcursionSuccess() {
//        ExcursionDTO excursionDTO = new ExcursionDTO();
//        Excursion existingExcursion = new Excursion();
//        when(excursionRepository.findById(1L)).thenReturn(Optional.of(existingExcursion));
//        when(excursionRepository.save(any(Excursion.class))).thenReturn(new Excursion());
//
//        Excursion updatedExcursion = excursionService.updateExcursion(1L, existingExcursion);
//
//        assertNotNull(updatedExcursion);
//        verify(excursionRepository).save(any(Excursion.class));
//    }
//
//    @Test
//    void testUpdateExcursionNotFound() {
//        Excursion updatedExcursion = new Excursion();
//        when(excursionRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(EntityNotFoundException.class, () -> excursionService.updateExcursion(1L, updatedExcursion));
//    }
//
//    @Test
//    void testDeleteExcursionSuccess() {
//        Excursion excursion = new Excursion();
//        when(excursionRepository.findById(1L)).thenReturn(Optional.of(excursion));
//
//        excursionService.deleteExcursion(1L);
//
//        verify(excursionRepository).delete(excursion);
//    }
//
//    @Test
//    void testDeleteExcursionNotFound() {
//        when(excursionRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(EntityNotFoundException.class, () -> excursionService.deleteExcursion(1L));
//    }
//}
