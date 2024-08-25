package com.tourist.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tourist.model.Reservation;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
    @OneToMany(mappedBy = "client" , cascade = CascadeType.ALL , orphanRemoval = true)
    @JsonIgnore
    private List<Reservation> reservations ;
}
