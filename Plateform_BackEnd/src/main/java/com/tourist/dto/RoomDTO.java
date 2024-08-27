package com.tourist.dto;

import com.tourist.enums.Type;
import com.tourist.model.Hotel;
import com.tourist.model.Reservation;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
    private Long id;
    private Type type;
    private Long price;
    private boolean available;
    private String image_path;
    private Hotel hotel;
    private Reservation reservation;
}
