package com.tourist.controller;

public class RoomController {
    @RestController
    @RequestMapping("/rooms")
    public class RoomController {

        @Autowired
        private RoomService roomService;

        // Ajouter une chambre à un hôtel
        @PostMapping("/add/{hotelId}")
        public ResponseEntity<RoomDTO> addRoomToHotel(@PathVariable Long hotelId, @RequestBody RoomDTO roomDTO) {
            RoomDTO newRoom = roomService.addRoomToHotel(hotelId, roomDTO);
            return new ResponseEntity<>(newRoom, HttpStatus.CREATED);
        }

        // Liste des chambres par ID d'hôtel
        @GetMapping("/by-hotel/{hotelId}")
        public ResponseEntity<List<RoomDTO>> listRoomsByHotelId(@PathVariable Long hotelId) {
            List<RoomDTO> rooms = roomService.listRoomsByHotelId(hotelId);
            return new ResponseEntity<>(rooms, HttpStatus.OK);
        }
    }

}
