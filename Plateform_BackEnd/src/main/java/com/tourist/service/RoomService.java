package com.tourist.service;

import com.tourist.dto.CloudinaryResponse;
import com.tourist.dto.RoomDTO;
import com.tourist.exception.HotelNotFoundException;
import com.tourist.model.Hotel;
import com.tourist.model.Image;
import com.tourist.model.Room;
import com.tourist.repository.HotelRepository;
import com.tourist.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

        Room room = new Room();
        room.setType(roomDTO.getType());
        room.setPrice(roomDTO.getPrice());
        room.setAvailable(roomDTO.isAvailable());
        room.setHotel(hotel);

        if (images != null && images.length > 0) {
            List<Image> imageList = processImages(images, room);
            room.setImages(imageList);
        }

        Room savedRoom = roomRepository.save(room);

        return new RoomDTO(
                savedRoom.getId(),
                savedRoom.getType(),
                savedRoom.getPrice(),
                savedRoom.isAvailable()


        );
    }

    private List<Image> processImages(MultipartFile[] images, Room room) {
        return Stream.of(images)
                .map(file -> {
                    CloudinaryResponse response = cloudinaryService.uploadFile(file);
                    return createImageEntity(response, room);
                })
                .collect(Collectors.toList());
    }

    private Image createImageEntity(CloudinaryResponse response, Room room) {
        Image image = new Image();
        image.setImageUrl(response.getUrl());
        image.setCloudinaryImageId(response.getPublicId());
        image.setRoom(room);
        return image;
    }

    public List<RoomDTO> listRoomsByHotelId(Long hotelId) {
        List<Room> rooms = roomRepository.findByHotel_IdHotel(hotelId);
        return rooms.stream()
                .map(room -> new RoomDTO(
                        room.getId(),
                        room.getType(),
                        room.getPrice(),
                        room.isAvailable(),
                        room.getImages(),
                        room.getHotel(),
                        room.getReservations()
                ))
                .collect(Collectors.toList());
    }
}
