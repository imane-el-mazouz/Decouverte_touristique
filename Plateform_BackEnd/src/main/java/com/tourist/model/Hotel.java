package com.tourist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tourist.enums.CategoryHotel;
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
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHotel;
    private String name;
    private String description;
    private String img;
    private String location ;
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 225)
    private CategoryHotel categoryHotel;

    @OneToMany(mappedBy = "hotel" , cascade = CascadeType.ALL , orphanRemoval = true )
    @JsonIgnore
    private List <Room> rooms ;

    private Double averageRating;
    private Double price;
    private Double distance;




//    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnore
//    private List<Reservation> reservations;
}
