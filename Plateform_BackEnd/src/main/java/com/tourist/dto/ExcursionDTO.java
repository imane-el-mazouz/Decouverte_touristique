package com.tourist.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tourist.model.Reservation;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.*;

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
    private String img;
    private LocalDateTime dateTime;
    private String location ;
    private Integer capacity;

    @OneToMany(mappedBy = "excursion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Reservation> reservations;
}
