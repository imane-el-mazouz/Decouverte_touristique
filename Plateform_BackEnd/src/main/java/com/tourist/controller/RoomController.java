package com.tourist.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tourist.dto.RoomDTO;
import com.tourist.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/room")
@CrossOrigin("*")

public class RoomController {


        @Autowired
        private RoomService roomService;

    @PostMapping(value = "/add/{hotelId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addRoomToHotel(
            @PathVariable Long hotelId,
            @RequestPart("room") RoomDTO roomDTO,
            @RequestPart(value = "images", required = false) MultipartFile[] images) {

        try {
           // System.out.println(Arrays.toString(Arrays.stream(images).toArray()));
            RoomDTO newRoom = roomService.addRoomToHotel(hotelId, roomDTO, images);
            return ResponseEntity.status(HttpStatus.CREATED).body(newRoom);
        } catch (RuntimeException e) {
            // Vous pouvez gérer des exceptions spécifiques comme `RuntimeException` ou créer des exceptions personnalisées
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hotel not found");
        } catch (Exception e) {
            // Gestion des exceptions générales
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }


    @GetMapping("/hotel/{hotelId}")
        public ResponseEntity<List<RoomDTO>> listRoomsByHotelId(@PathVariable Long hotelId) {
            List<RoomDTO> rooms = roomService.listRoomsByHotelId(hotelId);
            return new ResponseEntity<>(rooms, HttpStatus.OK);
    }
}

