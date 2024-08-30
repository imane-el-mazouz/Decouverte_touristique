package com.tourist.dto;

import com.tourist.enums.Type;
import com.tourist.model.Hotel;
import com.tourist.model.Image;
import com.tourist.model.Reservation;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
public class RoomDTO {
    private Long id;
    private Type type;
    private Long price;
    private boolean available;
    private List<Image> images;
    private Hotel hotel;
    private List<Reservation> reservations;

    public RoomDTO(Long id, Type type, Long price, boolean available, List<Image> images, Hotel hotel, List<Reservation> reservations) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.available = available;
        this.images = images;
        this.hotel = hotel;
        this.reservations = reservations;
    }
    public RoomDTO(Type type, Long price, boolean available, List<Image> images) {
        this.type = type;
        this.price = price;
        this.available = available;
        this.images = images;
    }

    public RoomDTO(Long id, Type type, Long price, boolean available) {
    }
}
