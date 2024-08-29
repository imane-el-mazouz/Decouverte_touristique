package com.tourist.controller;

import com.tourist.dto.HotelDTO;
import com.tourist.dto.HotelFilterDTO;
import com.tourist.enums.CategoryHotel;
import com.tourist.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/hotel")
@CrossOrigin("*")
public class HotelController {

    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<List<HotelDTO>> getAllHotels() {
        return ResponseEntity.ok(hotelService.getAllHotels());
    }

    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<HotelDTO> addHotel(@RequestBody HotelDTO hotelDTO) {
        return ResponseEntity.ok(hotelService.addHotel(hotelDTO));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<HotelDTO> getHotelById(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.getHotelById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<HotelDTO> updateHotel(@PathVariable Long id, @RequestBody HotelDTO hotelDTO) {
        return ResponseEntity.ok(hotelService.updateHotel(id, hotelDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter")
    @PreAuthorize("hasRole('Client') or hasRole('Admin')")
    public ResponseEntity<List<HotelDTO>> filterHotels(HotelFilterDTO filterDTO) {
        List<HotelDTO> hotels = hotelService.filterHotels(filterDTO);
        return ResponseEntity.ok(hotels);
    }
    @GetMapping("/search")
    @PreAuthorize("hasRole('Client') or hasRole('Admin')")
    public ResponseEntity<List<HotelDTO>> searchHotels(
            @RequestParam(required = false) CategoryHotel category,
            @RequestParam(required = false) String location) {
        return ResponseEntity.ok(hotelService.search(category, location));
    }
}
