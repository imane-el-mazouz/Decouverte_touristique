package com.tourist.controller;

import com.tourist.dto.ExcursionDTO;
import com.tourist.mapper.ExcursionMapper;
import com.tourist.model.Excursion;
import com.tourist.service.ExcursionService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/excursion")
@CrossOrigin("*")
public class ExcursionController {

    private final ExcursionService excursionService;
    private final ExcursionMapper excursionMapper;

    public ExcursionController(ExcursionMapper excursionMapper , ExcursionService excursionService) {
        this.excursionMapper = excursionMapper;
        this.excursionService = excursionService;
    }

    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Excursion> createExcursion(@RequestBody ExcursionDTO excursionDTO) {
        Excursion savedExcursion = excursionService.saveExcursion(excursionDTO);
        return ResponseEntity.ok(savedExcursion);
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
