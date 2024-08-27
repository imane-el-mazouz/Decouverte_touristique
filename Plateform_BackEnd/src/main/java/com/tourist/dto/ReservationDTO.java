package com.tourist.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tourist.enums.Status;
import com.tourist.model.Client;
import com.tourist.model.Event;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ReservationDTO {
    private Long numberOfPerson ;
    private LocalDateTime dateTime;
    private Date checkInDate;
    private Date checkOutDate;
    private boolean reservedRoom;
    private Status status;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "client_id")
    @JsonBackReference
    private Client client;


    @ManyToOne()
    @JoinColumn(name = "event_id")
    private Event event;
}
