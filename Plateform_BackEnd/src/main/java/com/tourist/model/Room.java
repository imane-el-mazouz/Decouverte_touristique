package com.tourist.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tourist.enums.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
     private Type type;
     private Long price;
     private boolean available;

     @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
     private List<Image> images;


     @ManyToOne()
     @JoinColumn(name = "hotel_id")
     @JsonIgnore
     private Hotel hotel;

     @OneToMany(mappedBy = "room" , cascade = CascadeType.ALL , orphanRemoval = true)
     @JsonIgnore
     private List<Reservation> reservations = new ArrayList<>();



}
