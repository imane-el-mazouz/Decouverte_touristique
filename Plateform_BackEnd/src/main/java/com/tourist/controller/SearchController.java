package com.tourist.controller;

import com.tourist.dto.SearchResultsDTO;
import com.tourist.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/search")
@CrossOrigin(origins = "*")

public class SearchController {
    @Autowired
    private SearchService searchService;

    @PostMapping
    public SearchResultsDTO search(@RequestBody Map<String, Object> criteria) {
        String location = (String) criteria.get("location");
        LocalDate date = LocalDate.parse((String) criteria.get("date"));
        String category = (String) criteria.get("category");

        return searchService.search(location, date, category);
    }
}
