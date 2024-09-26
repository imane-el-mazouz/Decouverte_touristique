package com.tourist.service;

import com.tourist.dto.HotelDTO;
import com.tourist.dto.HotelFilterDTO;
import com.tourist.dto.RoomDTO;
import com.tourist.enums.CategoryHotel;
import com.tourist.exception.HotelNotFoundException;
import com.tourist.model.Hotel;
import com.tourist.model.Room;
import com.tourist.repository.HotelRepository;
import com.tourist.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public HotelService(HotelRepository hotelRepository, RoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
    }

    public List<HotelDTO> getAllHotels() {
        return hotelRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public HotelDTO addHotel(HotelDTO hotelDTO) {
        Hotel hotel = convertToEntity(hotelDTO);
        return convertToDTO(hotelRepository.save(hotel));
    }

    public HotelDTO updateHotel(Long id, HotelDTO hotelDTO) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException(id));
        updateHotelFields(hotel, hotelDTO);
        return convertToDTO(hotelRepository.save(hotel));
    }

    public void deleteHotel(Long id) {
        if (!hotelRepository.existsById(id)) {
            throw new HotelNotFoundException(id);
        }
        hotelRepository.deleteById(id);
    }

    public List<HotelDTO> filterHotels(HotelFilterDTO filterDTO) {
        return hotelRepository.findAllByAverageRatingBetween(
                filterDTO.getMinRating(), filterDTO.getMaxRating()
        ).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public HotelDTO getHotelById(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException(id));
        return convertToDTO(hotel);
    }

    public List<HotelDTO> search(CategoryHotel category, String location) {
        return hotelRepository.findHotelByCategoryHotelOrLocation(
                category, location
        ).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private void updateHotelFields(Hotel hotel, HotelDTO hotelDTO) {
        hotel.setName(hotelDTO.getName());
        hotel.setDescription(hotelDTO.getDescription());
        hotel.setImg(hotelDTO.getImg());
        hotel.setLocation(hotelDTO.getLocation());
        hotel.setCategoryHotel(hotelDTO.getCategoryHotel());

        List<Room> updatedRooms = hotelDTO.getRooms().stream()
                .map(this::convertToEntity)
                .toList();

        hotel.getRooms().clear();
        hotel.getRooms().addAll(updatedRooms);

        hotel.setAverageRating(hotelDTO.getAverageRating());
        hotel.setPrice(hotelDTO.getPrice());
        hotel.setDistance(hotelDTO.getDistance());
    }


    private Hotel convertToEntity(HotelDTO hotelDTO) {
        Hotel hotel = new Hotel();
        hotel.setIdHotel(hotelDTO.getIdHotel());
        hotel.setName(hotelDTO.getName());
        hotel.setDescription(hotelDTO.getDescription());
        hotel.setImg(hotelDTO.getImg());
        hotel.setLocation(hotelDTO.getLocation());
        hotel.setCategoryHotel(hotelDTO.getCategoryHotel());
        hotel.setRooms(hotelDTO.getRooms().stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList()));
        hotel.setAverageRating(hotelDTO.getAverageRating());
        hotel.setPrice(hotelDTO.getPrice());
        hotel.setDistance(hotelDTO.getDistance());
        return hotel;
    }

    private HotelDTO convertToDTO(Hotel hotel) {
        return HotelDTO.builder()
                .idHotel(hotel.getIdHotel())
                .name(hotel.getName())
                .description(hotel.getDescription())
                .img(hotel.getImg())
                .location(hotel.getLocation())
                .categoryHotel(hotel.getCategoryHotel())
                .rooms(hotel.getRooms().stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList()))
                .averageRating(hotel.getAverageRating())
                .price(hotel.getPrice())
                .distance(hotel.getDistance())
                .build();
    }

//    private Room convertToEntity(RoomDTO roomDTO) {
//        Room room = new Room();
//        room.setId(roomDTO.getId());
//        room.setType(roomDTO.getType());
//        room.setPrice(roomDTO.getPrice());
//        room.setAvailable(roomDTO.isAvailable());
//
//        // Convertir les URLs des images en objets Image avant de les ajouter à la chambre
//        List<Image> images = roomDTO.getImageUrls().stream()
//                .map(url -> {
//                    Image image = new Image();
//                    image.setImageUrl(url);
//                    image.setRoom(room); // Associer l'image à la chambre
//                    return image;
//                })
//                .collect(Collectors.toList());
//        room.setImages(images);
//
//        return room;
//    }
private Room convertToEntity(RoomDTO roomDTO) {
    Room room = new Room();
    room.setId(roomDTO.getId());
    room.setType(roomDTO.getType());
    room.setPrice(roomDTO.getPrice());
    room.setAvailable(roomDTO.isAvailable());

    room.setImages(roomDTO.getImageUrls());

    return room;
}


    private RoomDTO convertToDTO(Room room) {
        return RoomDTO.builder()
                .id(room.getId())
                .type(room.getType())
                .price(room.getPrice())
                .available(room.isAvailable())
                .imageUrls(room.getImages()) // Directly use the list of image URLs
                .build();
    }


}
