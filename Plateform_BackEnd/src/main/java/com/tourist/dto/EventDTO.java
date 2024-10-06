package com.tourist.dto;

import com.tourist.enums.CategoryEvent;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventDTO {
    private String name;
    private String description;
    private String imgPath;
    private LocalDate date;
    private String location;
    private Integer capacity;
    private Double price;
    private Integer rating;
    private Double distance;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 225)
    private CategoryEvent category;

    private List<ReservationDTO> reservations;

    public EventDTO(String name, String description, String imgPath, LocalDate date, String location, Integer capacity, CategoryEvent category, Object o) {
        this.name = name;
        this.description = description;
        this.imgPath = imgPath;
        this.date = date;
        this.location = location;
        this.capacity = capacity;
        this.category = category;
        this.reservations = new ArrayList<>();
    }

    public EventDTO(String name, CategoryEvent category, String location, LocalDate date) {
        this.name = name;
        this.category = category;
        this.location = location;
        this.date = date;
    }
}
