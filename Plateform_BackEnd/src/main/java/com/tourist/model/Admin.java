package com.tourist.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@DiscriminatorValue("Admin")
@Getter
@Setter
public class Admin extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long totalClients ;
    private Long totalEvents ;
    private Long totalReservations ;


}
