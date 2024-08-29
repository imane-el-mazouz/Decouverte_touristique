package com.tourist.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tourist.enums.CategoryEvent;
import com.tourist.model.Reservation;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class EventDTO {
    private Long id ;
    private String name;
    private String description;
    private String img;
    private LocalDate date;
    private String location;
    private Integer capacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 225)
    private CategoryEvent category;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Reservation> reservations;
}
