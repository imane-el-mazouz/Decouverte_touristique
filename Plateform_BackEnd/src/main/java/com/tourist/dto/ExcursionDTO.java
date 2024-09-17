package com.tourist.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tourist.model.Reservation;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExcursionDTO {
    private String name;
    private String description;
    private String imgPath;
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



    public ExcursionDTO(String name, String description,String imgPath , LocalDateTime dateTime, String location, Integer capacity, Long rating) {
        this.name = name;
        this.description = description;
        this.imgPath = imgPath;
        this.dateTime = dateTime;
        this.location = location;
        this.capacity = capacity;
        this.rating = rating;
    }

}
