package com.tourist.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;


@Entity
@DiscriminatorValue("Admin")
@Getter
@Setter
public class Admin extends Person {




}
