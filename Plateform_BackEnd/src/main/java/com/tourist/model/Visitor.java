package com.tourist.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("Visitor")
@Getter
@Setter
public class Visitor extends Person{


}
