package com.tourist.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tourist.model.Client;
import com.tourist.model.Reservation;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ReviewDTO {

    private Long rating ;
    private String comment ;

    private LocalDate date ;

    @JsonIgnore
    private Client client;

    @JsonIgnore
    private Reservation reservation;

}
