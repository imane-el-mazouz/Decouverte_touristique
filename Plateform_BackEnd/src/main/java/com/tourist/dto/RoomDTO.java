package com.tourist.dto;

import com.tourist.enums.Type;
import com.tourist.model.Hotel;
import com.tourist.model.Reservation;
import lombok.*;

import java.util.List;

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
    private List<String> imageUrls; // Store image URLs as Strings
    private Hotel hotel; // Include hotel for potential future use
    private List<Reservation> reservations; // Include reservations for future use

    // Constructor without Hotel and Reservations
    public RoomDTO(Type type, Long price, boolean available, List<String> imageUrls) {
        this.type = type;
        this.price = price;
        this.available = available;
        this.imageUrls = imageUrls;
    }

    // Simplified Constructor without images, hotel, or reservations
    public RoomDTO(Long id, Type type, Long price, boolean available) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.available = available;
    }

    public RoomDTO(Long id, Type type, Long price, boolean available, List<String> images) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.available = available;
        this.imageUrls = images; // Assigning the images parameter to the imageUrls field
    }

}
