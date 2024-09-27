package com.tourist.service;

import com.tourist.dto.CloudinaryResponse;
import com.tourist.dto.RoomDTO;
import com.tourist.exception.HotelNotFoundException;
import com.tourist.model.Hotel;
import com.tourist.model.Room;
import com.tourist.repository.HotelRepository;
import com.tourist.repository.RoomRepository;
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

//    public AnnonceResponseDTO createAnnonce(AnnonceCreateDTO annonceDTO, Utilisateur user, MultipartFile[] images) throws IOException {
//        List<String> imageUrls = cloudinaryService.uploadImages(images);
//
//        Annonce annonce = new Annonce();
//        annonce.setTitle(annonceDTO.getTitle());
//        annonce.setDescription(annonceDTO.getDescription());
//        annonce.setPrice(annonceDTO.getPrice());
//        annonce.setCategory(annonceDTO.getCategory());
//        annonce.setCreationDate(LocalDateTime.now());
//        annonce.setDisponibilite(annonceDTO.getDisponibilite());
//        annonce.setImages(imageUrls);
//        annonce.setVendeur(user);
//        annonce = annonceRepository.save(annonce);
//        return mapToResponseDTO(annonce);
//    }
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
            savedRoom.getImages() // Include the saved image URLs
    );
}

//        return new RoomDTO(
//                savedRoom.getId(),
//                savedRoom.getType(),
//                savedRoom.getPrice(),
//                savedRoom.isAvailable(),
//                imageList != null ? imageList.stream().map(Image::getImageUrl).collect(Collectors.toList()) : Collections.emptyList() // Return image URLs in DTO
//        );


//    private List<Image> processImages(MultipartFile[] images, Room room) {
//        return Stream.of(images)
//                .map(file -> {
//                    CloudinaryResponse response = cloudinaryService.uploadFile(file);
//                    return createImageEntity(response, room);
//                })
//                .collect(Collectors.toList());
//    }
//
//    private Image createImageEntity(CloudinaryResponse response, Room room) {
//        Image image = new Image();
//        image.setImageUrl(response.getUrl());
//        image.setCloudinaryImageId(response.getPublicId());
//        image.setRoom(room);
//        return image;
//    }

    public List<RoomDTO> listRoomsByHotelId(Long hotelId) {
        // Retrieve the list of rooms by hotel ID
        List<Room> rooms = roomRepository.findByHotel_IdHotel(hotelId);

        // Convert each Room entity to RoomDTO and process image URLs
        return rooms.stream()
                .map(room -> {
                    List<String> imageUrls = room.getImages(); // Get image URLs

                    // Reverse the order of image URLs (optional, based on your needs)
                    Collections.reverse(imageUrls);

                    // Map the Room entity to RoomDTO
                    return new RoomDTO(
                            room.getId(),
                            room.getType(),
                            room.getPrice(),
                            room.isAvailable(),
                            imageUrls, // Reversed image URLs
                            room.getHotel(), // Set the hotel if needed
                            room.getReservations() // Set reservations if needed
                    );
                })
                .collect(Collectors.toList()); // Collect the result as a list of RoomDTOs
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
