package com.tourist.service;

import org.springframework.stereotype.Service;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository, HotelRepository hotelRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
    }

    // Ajouter une chambre à un hôtel
    public RoomDTO addRoomToHotel(Long hotelId, RoomDTO roomDTO) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        Room room = new Room();
        room.setType(roomDTO.getType());
        room.setPrice(roomDTO.getPrice());
        room.setAvailable(roomDTO.isAvailable());
        room.setImagePath(roomDTO.getImagePath());
        room.setHotel(hotel);

        Room savedRoom = roomRepository.save(room);

        return new RoomDTO(
                savedRoom.getId(),
                savedRoom.getType(),
                savedRoom.getPrice(),
                savedRoom.isAvailable(),
                savedRoom.getImagePath(),
                savedRoom.getHotel(),
                savedRoom.getReservation()
        );
    }

    // Liste des chambres par ID d'hôtel
    public List<RoomDTO> listRoomsByHotelId(Long hotelId) {
        List<Room> rooms = roomRepository.findByHotelId(hotelId);
        return rooms.stream()
                .map(room -> new RoomDTO(
                        room.getId(),
                        room.getType(),
                        room.getPrice(),
                        room.isAvailable(),
                        room.getImagePath(),
                        room.getHotel(),
                        room.getReservation()
                ))
                .collect(Collectors.toList());
    }
}
