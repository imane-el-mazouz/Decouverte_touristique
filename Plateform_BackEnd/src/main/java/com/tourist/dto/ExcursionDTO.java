package com.tourist.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tourist.model.Reservation;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExcursionDTO {
    private String name;
    private String description;
    private MultipartFile img;
    private LocalDateTime dateTime;
    private String location;
    private Integer capacity;
    private Long rating;

    @OneToMany(mappedBy = "excursion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Reservation> reservations;



    public ExcursionDTO(String name, String location, LocalDateTime dateTime) {
        this.name = name ;
        this.location= location;
        this.dateTime = dateTime;
    }



    public ExcursionDTO(String name, String description, MultipartFile img, LocalDateTime dateTime, String location, Integer capacity, Long rating) {
        this.name = name;
        this.description = description;
        this.img = img;
        this.dateTime = dateTime;
        this.location = location;
        this.capacity = capacity;
        this.rating = rating;
    }

}
