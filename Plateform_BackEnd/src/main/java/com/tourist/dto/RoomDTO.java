package com.tourist.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tourist.enums.Type;
import com.tourist.model.Hotel;
import com.tourist.model.Reservation;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoomDTO {

    private Type type;
    private Long price ;
    private List<String> equipements = new ArrayList<>();
    private boolean available;
    String image_path;


    @ManyToOne()
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @OneToMany(mappedBy = "room" , cascade = CascadeType.ALL , orphanRemoval = true)
    @JsonIgnore
    private List<Reservation> reservations;
}
