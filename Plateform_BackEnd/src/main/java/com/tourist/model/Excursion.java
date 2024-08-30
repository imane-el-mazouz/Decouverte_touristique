package com.tourist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Excursion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idExcursion;
    private String name;
    private String description;
    private String imgPath;  // Updated to store the image path
    private LocalDateTime dateTime;
    private String location;
    private Integer capacity;
    private Long rating;


    @OneToMany(mappedBy = "excursion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Reservation> reservations;
}
