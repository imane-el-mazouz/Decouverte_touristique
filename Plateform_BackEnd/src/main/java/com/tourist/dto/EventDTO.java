package com.tourist.dto;

import com.tourist.enums.CategoryEvent;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    private Long id;
    private String name;
    private String description;
    private String imgPath;
    private LocalDate date;
    private String location;
    private Integer capacity;
    private CategoryEvent category;
    private List<ReservationDTO> reservations;

    public EventDTO(String name, String description, String imgPath, LocalDate date, String location, Integer capacity, CategoryEvent category, Object o) {
    }

    public EventDTO(String name, CategoryEvent category, String location, LocalDate date) {
    }
}
