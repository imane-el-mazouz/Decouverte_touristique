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
    private String img;
    private LocalDateTime dateTime;
    private String location ;
    private Integer capacity;
//    @Enumerated(EnumType.STRING)
//    @Column(name = "category", nullable = false, length = 225)
//    private CategoryExcursion categoryExcursion;

    @OneToMany(mappedBy = "excursion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Reservation> reservations;

}
