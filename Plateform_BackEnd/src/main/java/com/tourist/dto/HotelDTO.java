package com.tourist.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tourist.enums.CategoryHotel;
import com.tourist.model.Room;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class HotelDTO {
    private String name;
    private String description;
    private String img;
    private String location ;
    private Integer capacity;
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 225)
    private CategoryHotel categoryHotel;

    @OneToMany(mappedBy = "hotel" , cascade = CascadeType.ALL , orphanRemoval = true )
    @JsonIgnore
    private List<Room> rooms ;
}
