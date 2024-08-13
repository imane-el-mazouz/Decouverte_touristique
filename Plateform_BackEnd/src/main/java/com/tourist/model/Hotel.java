package com.tourist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tourist.enums.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idE;
    private String name;
    private String description;
    private String img;
    private LocalDateTime dateTime;
    private String location ;
    private Integer capacity;
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 225)
    private Category category;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Reservation> reservations;
}
