package com.tourist.dto;

import com.tourist.enums.CategoryHotel;
import com.tourist.model.Hotel;
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

}
