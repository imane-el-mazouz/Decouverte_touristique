package com.tourist.service;

import com.tourist.dto.HotelDTO;
import com.tourist.dto.ReviewDTO;
import com.tourist.dto.RoomDTO;
import com.tourist.enums.CategoryHotel;
import com.tourist.model.*;
import com.tourist.repository.HotelRepository;
import com.tourist.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelService {


    private final HotelRepository hotelRepository;

    @Autowired
    private RoomRepository roomRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }


    public List<HotelDTO> getAllHotels(){
        List<Hotel> hotels = hotelRepository.findAll();
        return hotels.stream().map(this::convertToDTO).collect(Collectors.toList());
    }



    public Hotel addHotel(HotelDTO hotelDTO ){
        Hotel hotel = new Hotel();
        hotel.setIdHotel(hotelDTO.getIdHotel());
        hotel.setName(hotelDTO.getName());
        hotel.setDescription(hotelDTO.getDescription());
        hotel.setImg(hotelDTO.getImg());
        hotel.setLocation(hotelDTO.getLocation());
        hotel.setCategoryHotel(hotelDTO.getCategoryHotel());
        List<Room> rooms = hotelDTO.getRooms().stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
        hotel.setRooms(rooms);
        return hotelRepository.save(hotel);
    }

    private HotelDTO convertToDTO(Hotel hotel) {
        return HotelDTO.builder()
                .idHotel(hotel.getIdHotel())
                .name(hotel.getName())
                .description(hotel.getDescription())
                .img(hotel.getImg())
                .location(hotel.getLocation())
                .categoryHotel(hotel.getCategoryHotel())
                .rooms(hotel.getRooms().stream().map(this::convertToDTO).collect(Collectors.toList()))
                .build();
    }

    private RoomDTO convertToDTO(Room room) {
        return RoomDTO.builder()
                .id(room.getId())
                .type(room.getType())
                .price(room.getPrice())
                .available(room.isAvailable())
                .image_path(room.getImage_path())
                .build();
    }

    private Room convertToEntity(RoomDTO roomDTO) {
        Room room = new Room();
        room.setId(roomDTO.getId());
        room.setType(roomDTO.getType());
        room.setPrice(roomDTO.getPrice());
        room.setAvailable(roomDTO.isAvailable());
        room.setImage_path(roomDTO.getImage_path());
        return room;
    }

    public List<HotelDTO> filterHotels(String location, Double price, CategoryHotel categoryHotel) {
        List<Hotel> hotels = hotelRepository.filterHotels(location, price, categoryHotel);
        return hotels.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}
