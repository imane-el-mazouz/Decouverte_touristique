package com.tourist.dto;

import com.tourist.enums.CategoryHotel;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelDTO {
    private Long idHotel;
    private String name;
    private String description;
    private String img;
    private String location;
    private CategoryHotel categoryHotel;
    private List<RoomDTO> rooms;
    private Double averageRating;
    private Double price;
    private Double distance;

    public HotelDTO(String name, CategoryHotel categoryHotel, String location) {
    }
}
