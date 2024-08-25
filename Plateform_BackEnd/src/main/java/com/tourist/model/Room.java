package com.tourist.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tourist.enums.Type;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Room {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
     private Type type;
     private Long price;
//     private List<String> equipements = new ArrayList<>();
     private boolean available;
     private String image_path;


     @ManyToOne()
     @JoinColumn(name = "hotel_id")
     private Hotel hotel;

     @OneToMany(mappedBy = "room" , cascade = CascadeType.ALL , orphanRemoval = true)
     @JsonIgnore
     private List<Reservation> reservations = new ArrayList<>();







     
}
