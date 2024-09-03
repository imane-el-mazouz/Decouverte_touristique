//package com.tourist;
//
//import com.tourist.dto.HotelDTO;
//import com.tourist.dto.HotelFilterDTO;
//import com.tourist.dto.RoomDTO;
//import com.tourist.enums.CategoryHotel;
//import com.tourist.exception.HotelNotFoundException;
//import com.tourist.model.Hotel;
//import com.tourist.model.Room;
//import com.tourist.repository.HotelRepository;
//import com.tourist.repository.RoomRepository;
//import com.tourist.service.HotelService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//class HotelTest {
//
//    @Mock
//    private HotelRepository hotelRepository;
//
//    @Mock
//    private RoomRepository roomRepository;
//
//    @InjectMocks
//    private HotelService hotelService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testGetAllHotels() {
//        List<Hotel> hotels = new ArrayList<>();
//        hotels.add(new Hotel());
//        when(hotelRepository.findAll()).thenReturn(hotels);
//
//        List<HotelDTO> hotelDTOs = hotelService.getAllHotels();
//
//        assertNotNull(hotelDTOs);
//    }
//
//
//
//
//    @Test
//    void testUpdateHotelNotFound() {
//        HotelDTO hotelDTO = new HotelDTO();
//        when(hotelRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(HotelNotFoundException.class, () -> hotelService.updateHotel(1L, hotelDTO));
//    }
//
//    @Test
//    void testDeleteHotelSuccess() {
//        when(hotelRepository.existsById(1L)).thenReturn(true);
//    }
//
//    @Test
//    void testDeleteHotelNotFound() {
//        when(hotelRepository.existsById(1L)).thenReturn(false);
//        assertThrows(HotelNotFoundException.class, () -> hotelService.deleteHotel(1L));
//    }
//
//    @Test
//    void testFilterHotels() {
//        HotelFilterDTO filterDTO = new HotelFilterDTO();
//        List<Hotel> hotels = new ArrayList<>();
//        hotels.add(new Hotel());
//        when(hotelRepository.findAllByAverageRatingBetween(1L, 5L)).thenReturn(hotels);
//        List<HotelDTO> filteredHotels = hotelService.filterHotels(filterDTO);
//        assertNotNull(filteredHotels);
//    }
//
//    @Test
//    void testGetHotelByIdNotFound() {
//        when(hotelRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(HotelNotFoundException.class, () -> hotelService.getHotelById(1L));
//    }
//
//    @Test
//    void testSearch() {
//        CategoryHotel category = CategoryHotel.bohemian;
//        String location = "Paris";
//        List<Hotel> hotels = new ArrayList<>();
//        hotels.add(new Hotel());
//        when(hotelRepository.findHotelByCategoryHotelOrLocation(category, location)).thenReturn(hotels);
//
//        List<HotelDTO> result = hotelService.search(category, location);
//
//        assertNotNull(result);
//    }
//}
