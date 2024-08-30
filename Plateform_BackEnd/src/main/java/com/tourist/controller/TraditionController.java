package com.tourist.controller;

import com.tourist.dto.TraditionDTO;
import com.tourist.model.Tradition;
import com.tourist.service.TraditionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tradition")
@CrossOrigin("*")

public class TraditionController {
    private final TraditionService traditionService;
    public TraditionController(TraditionService traditionService){
        this.traditionService=traditionService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<TraditionDTO> addTraditionWithBlogs(@RequestBody TraditionDTO traditionDTO) {
        if (traditionDTO.getBlogs() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        TraditionDTO savedTradition = traditionService.createTraditionWithBlogs(traditionDTO);
        return new ResponseEntity<>(savedTradition, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('Admin') or hasRole('Client')")
    public ResponseEntity<List<Tradition>> listTraditions() {
        List<Tradition> traditions = traditionService.listTraditions();
        return ResponseEntity.ok(traditions);
    }
}
