package com.tourist.service;

import com.tourist.dto.CloudinaryResponse;
import com.tourist.dto.RoomDTO;
import com.tourist.exception.HotelNotFoundException;
import com.tourist.model.Hotel;
import com.tourist.model.Room;
import com.tourist.repository.HotelRepository;
import com.tourist.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final CloudinaryService cloudinaryService;

    public RoomService(RoomRepository roomRepository, HotelRepository hotelRepository, CloudinaryService cloudinaryService) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
        this.cloudinaryService = cloudinaryService;
    }
@Transactional
public RoomDTO addRoomToHotel(Long hotelId, RoomDTO roomDTO, MultipartFile[] images) {
    Hotel hotel = hotelRepository.findById(hotelId)
            .orElseThrow(() -> new HotelNotFoundException(hotelId));

    List<String> imageUrls = cloudinaryService.uploadFile(images);
    Room room = new Room();
    room.setType(roomDTO.getType());
    room.setPrice(roomDTO.getPrice());
    room.setAvailable(roomDTO.isAvailable());
    room.setImages(imageUrls);
    room.setHotel(hotel);

    Room savedRoom = roomRepository.save(room);

    return new RoomDTO(
            savedRoom.getId(),
            savedRoom.getType(),
            savedRoom.getPrice(),
            savedRoom.isAvailable(),
            savedRoom.getImages()
    );
}

    public List<RoomDTO> listRoomsByHotelId(Long hotelId) {
        List<Room> rooms = roomRepository.findByHotel_IdHotel(hotelId);

        return rooms.stream()
                .map(room -> {
                    List<String> imageUrls = room.getImages();

                    Collections.reverse(imageUrls);

                    return new RoomDTO(
                            room.getId(),
                            room.getType(),
                            room.getPrice(),
                            room.isAvailable(),
                            imageUrls,
                            room.getHotel(),
                            room.getReservations()
                    );
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public RoomDTO updateRoom(Long roomId, RoomDTO roomDTO, MultipartFile[] images) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("roomId with this id id not exist"));

        if (roomDTO != null) {
            room.setType(roomDTO.getType());
            room.setPrice(roomDTO.getPrice());
            room.setAvailable(roomDTO.isAvailable());
        }

        if (images != null && images.length > 0) {
            List<String> imageUrls = cloudinaryService.uploadFile(images);
            room.setImages(imageUrls);
        }

        Room updatedRoom = roomRepository.save(room);

        return new RoomDTO(
                updatedRoom.getId(),
                updatedRoom.getType(),
                updatedRoom.getPrice(),
                updatedRoom.isAvailable(),
                updatedRoom.getImages()
        );
    }

    @Transactional
    public void deleteRoom(Long roomId) {
        roomRepository.deleteById(roomId);
    }
    @Transactional(readOnly = true)
    public RoomDTO getRoomById(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found with id: " + roomId));

        return new RoomDTO(
                room.getId(),
                room.getType(),
                room.getPrice(),
                room.isAvailable(),
                room.getImages()
        );
    }


//    public List<RoomDTO> listRoomsByHotelId(Long hotelId) {
//        List<Room> rooms = roomRepository.findByHotel_IdHotel(hotelId);
//        return rooms.stream()
//                .map(room -> {
////                    List<String> imageUrls = room.getImages().stream()
////                            .map(Image::getImageUrl)
////                            .collect(Collectors.toList());
//
//                    // Reverse the order of image URLs
//                    Collections.reverse(imageUrls);
//
//                    return new RoomDTO(
//                            room.getId(),
//                            room.getType(),
//                            room.getPrice(),
//                            room.isAvailable(),
//                            imageUrls, // Reversed image URLs
//                            room.getHotel(),
//                            room.getReservations()
//                    );
//                })
//                .collect(Collectors.toList());
//    }



}
