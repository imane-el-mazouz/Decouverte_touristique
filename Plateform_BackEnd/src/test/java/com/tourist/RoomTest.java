//package com.tourist;
//
//import com.tourist.dto.CloudinaryResponse;
//import com.tourist.dto.RoomDTO;
//import com.tourist.enums.Type;
//import com.tourist.exception.HotelNotFoundException;
//import com.tourist.model.Hotel;
//import com.tourist.model.Room;
//import com.tourist.repository.HotelRepository;
//import com.tourist.repository.RoomRepository;
//import com.tourist.service.CloudinaryService;
//import com.tourist.service.RoomService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//class RoomTest {
//
//    @Mock
//    private RoomRepository roomRepository;
//
//    @Mock
//    private HotelRepository hotelRepository;
//
//    @Mock
//    private CloudinaryService cloudinaryService;
//
//    @InjectMocks
//    private RoomService roomService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testAddRoomToHotelHotelNotFound() {
//        Long hotelId = 1L;
//        RoomDTO roomDTO = new RoomDTO();
//        roomDTO.setType(Type.valueOf("couple"));
//        roomDTO.setPrice((long) 100.0);
//        roomDTO.setAvailable(true);
//
//        when(hotelRepository.findById(hotelId)).thenReturn(Optional.empty());
//
//        assertThrows(HotelNotFoundException.class, () -> roomService.addRoomToHotel(hotelId, roomDTO, null));
//    }
//
//    @Test
//    void testListRoomsByHotelId() {
//        Long hotelId = 1L;
//        Hotel hotel = new Hotel();
//        Room room = new Room();
//        room.setId(1L);
//        room.setType(Type.couple);
//        room.setPrice((long) 300.0);
//        room.setAvailable(true);
//        room.setHotel(hotel);
//
//        List<Room> rooms = new ArrayList<>();
//        rooms.add(room);
//
//        when(roomRepository.findByHotel_IdHotel(hotelId)).thenReturn(rooms);
//        List<RoomDTO> result = roomService.listRoomsByHotelId(hotelId);
//        assertNotNull(result);
//    }
//}
