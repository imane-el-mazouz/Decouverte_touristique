package com.tourist.controller;

import com.tourist.dto.ExcursionDTO;
import com.tourist.model.Excursion;
import com.tourist.service.ExcursionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/excursion")
@CrossOrigin("*")
public class ExcursionController {

    @Autowired
    private ExcursionService excursionService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('Admin')")
//    public ResponseEntity<Excursion> addExcursion(@ModelAttribute ExcursionDTO excursionDTO) throws IOException {
//        Excursion excursion = excursionService.saveExcursion(excursionDTO);
//        return ResponseEntity.ok(excursion);
//    }
    public ResponseEntity<Excursion> addExcursion(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
            @RequestParam("location") String location,
            @RequestParam("capacity") Integer capacity,
            @RequestParam("rating") Long rating,
            @RequestParam(value = "img", required = false) MultipartFile img
    ) throws IOException {
        ExcursionDTO excursionDTO = new ExcursionDTO(name, description, img != null ? img : null, dateTime, location, capacity, rating);
        if (img != null) {
            excursionService.saveImage(img);
        }
        Excursion excursion = excursionService.saveExcursion(excursionDTO);
        return ResponseEntity.ok(excursion);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('Admin') or hasRole('Client')")
    public ResponseEntity<List<Excursion>> getAllExcursions() {
        List<Excursion> excursions = excursionService.getAllExcursions();
        return ResponseEntity.ok(excursions);
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('Admin') or hasRole('Client')")
    public ResponseEntity<List<Excursion>> searchExcursions(
            @RequestParam(required = false) LocalDateTime date,
            @RequestParam(required = false) String location) {
        List<Excursion> excursions = excursionService.searchExcursions(date, location);
        return ResponseEntity.ok(excursions);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasRole('Admin') or hasRole('Client')")
    public ResponseEntity<List<Excursion>> filterExcursionsByRating(
            @RequestParam Long minRating,
            @RequestParam Long maxRating) {
        List<Excursion> excursions = excursionService.filterExcursionsByRating(minRating, maxRating);
        return ResponseEntity.ok(excursions);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Excursion> getExcursionById(@PathVariable Long id) {
        Excursion excursion = excursionService.getExcursionById(id);
        return ResponseEntity.ok(excursion);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Excursion> updateExcursion(
            @PathVariable Long id,
            @RequestBody Excursion updatedExcursion) {
        Excursion excursion = excursionService.updateExcursion(id, updatedExcursion);
        return ResponseEntity.ok(excursion);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> deleteExcursion(@PathVariable Long id) {
        excursionService.deleteExcursion(id);
        return ResponseEntity.noContent().build();
    }
}
