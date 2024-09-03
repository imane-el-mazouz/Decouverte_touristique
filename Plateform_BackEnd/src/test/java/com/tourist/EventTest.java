//package com.tourist;
//
//import com.tourist.dto.EventDTO;
//import com.tourist.dto.EventFilterDTO;
//import com.tourist.enums.CategoryEvent;
//import com.tourist.exception.EventAlreadyExistsException;
//import com.tourist.exception.EventNotFoundException;
//import com.tourist.model.Event;
//import com.tourist.repository.EventRepository;
//import com.tourist.service.EventService;
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
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//
//@SpringBootTest
//class EventTest {
//
//    @Mock
//    private EventRepository eventRepository;
//
//    @InjectMocks
//    private EventService eventService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//
//    @Test
//    void testDeleteEventNotFound() {
//        when(eventRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(EventNotFoundException.class, () -> eventService.deleteEvent(1L));
//    }
//
//
//
//    @Test
//    void testUpdateEventNotFound() {
//        EventDTO eventDTO = new EventDTO();
//        when(eventRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(EventNotFoundException.class, () -> eventService.updateEvent(1L, eventDTO));
//    }
//
//
//    @Test
//    void testSaveImageIOException() throws IOException {
//        MultipartFile file = mock(MultipartFile.class);
//        when(file.getOriginalFilename()).thenReturn("test-image.jpg");
//
//        doThrow(new IOException()).when(file).transferTo(any(File.class));
//
//        assertThrows(IOException.class, () -> eventService.saveImage(file));
//    }
//}
