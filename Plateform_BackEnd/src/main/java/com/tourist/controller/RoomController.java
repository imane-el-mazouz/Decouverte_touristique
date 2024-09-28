package com.tourist.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tourist.dto.RoomDTO;
import com.tourist.exception.HotelNotFoundException;
import com.tourist.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/room")
@CrossOrigin("*")

public class RoomController {

        @Autowired
        private RoomService roomService;


    @GetMapping("/hotel/{hotelId}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<List<RoomDTO>> listRoomsByHotelId(@PathVariable Long hotelId) {
            List<RoomDTO> rooms = roomService.listRoomsByHotelId(hotelId);
            return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

//    @PostMapping(value = "/add/{hotelId}" ,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.APPLICATION_JSON_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasRole('Admin')")
//    public ResponseEntity<List<RoomDTO>> addRoomToHotel(@PathVariable("hotelId") Long hotelId,
//                                                        @RequestPart("room") RoomDTO roomDTO,
//                                                        @RequestPart("images") @Nullable MultipartFile[] images
//                                                        ) {
//        System.out.println("/////////////////////////////////"+hotelId+roomDTO.getPrice());
//
//
//
//        List<RoomDTO> rooms = roomService.listRoomsByHotelId(hotelId);
//        return new ResponseEntity<>(rooms, HttpStatus.OK);
//    }


    @PostMapping(value = "/create/{hotelId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<RoomDTO> addRoomToHotel(@PathVariable("hotelId") Long hotelId,
                                                  @RequestParam(value = "room", required = false) String roomDTOJson,
                                                  @RequestParam(value = "images", required = false) MultipartFile[] images) {

        try {
            RoomDTO roomDTO = null;
            if (roomDTOJson != null) {roomDTO = new ObjectMapper().readValue(roomDTOJson, RoomDTO.class);
            }
            assert roomDTO != null;
            RoomDTO savedRoomDTO = roomService.addRoomToHotel(hotelId, roomDTO, images);

            return ResponseEntity.ok(savedRoomDTO);

        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (HotelNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


//    @PostMapping(value = "/add/{hotelId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasRole('Admin')")
//    public ResponseEntity<RoomDTO> addRoomToHotel(@PathVariable("hotelId") Long hotelId,
//                                                  @RequestParam("room") String roomDTOJson,
//                                                  @RequestParam("images") MultipartFile[] images) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        RoomDTO roomDTO;
//        try {
//            roomDTO = objectMapper.readValue(roomDTOJson, RoomDTO.class);
//        } catch (JsonProcessingException e) {
//            System.out.println("Failed to parse RoomDTO JSON: " + e.getMessage());
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        System.out.println("User has role Admin: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
//        System.out.println("Hotel ID: " + hotelId);
//        System.out.println("Received RoomDTO: " + roomDTO);
//        System.out.println("Price: " + roomDTO.getPrice());
//        System.out.println("Available: " + roomDTO.isAvailable());
//        System.out.println("Type: " + roomDTO.getType());
//        System.out.println("Number of images received: " + images.length);
//
//        for (int i = 0; i < images.length; i++) {
//            MultipartFile image = images[i];
//            System.out.println("Image " + (i + 1) + ":");
//            System.out.println(" - Original Filename: " + image.getOriginalFilename());
//            System.out.println(" - Content Type: " + image.getContentType());
//            System.out.println(" - Size: " + image.getSize() + " bytes");
//        }
//        RoomDTO savedRoomDTO = roomService.addRoomToHotel(hotelId, roomDTO, images);
//        return new ResponseEntity<>(savedRoomDTO, HttpStatus.OK);
//    }
//

}

