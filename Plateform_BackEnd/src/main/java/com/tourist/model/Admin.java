package com.tourist.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@DiscriminatorValue("Admin")
@Getter
@Setter
public class Admin extends Person {

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contact> contacts;

}
