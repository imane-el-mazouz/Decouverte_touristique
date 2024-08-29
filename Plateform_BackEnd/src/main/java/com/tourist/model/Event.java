package com.tourist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.tourist.enums.CategoryEvent;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvent;
    private String name;
    private String description;
    private String img;
    private LocalDate date;
    private String location;
    private Integer capacity;
    private Double price;
    private Integer rating;
    private Double distance;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 225)
    private CategoryEvent category;


    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Reservation> reservations;

}
