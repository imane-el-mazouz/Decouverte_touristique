package com.tourist.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tourist.enums.CategoryEvent;
import com.tourist.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long numberOfPerson ;
    private LocalDate dateTime;
    private Date checkInDate;
    private Date checkOutDate;
    private boolean reservedRoom;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 225)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "excursion_id")
    @JsonIgnore
    private Excursion excursion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    @JsonIgnore
    private Client client;

    @ManyToOne
    @JoinColumn(name = "event_id")
    @JsonIgnore

    private Event event;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonIgnore
    private Room room;

    @OneToMany(mappedBy = "reservation" , cascade = CascadeType.ALL , orphanRemoval = true)
    @JsonIgnore
    private List<Review> reviews ;
}
