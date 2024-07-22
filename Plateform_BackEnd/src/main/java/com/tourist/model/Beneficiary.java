package com.tourist.model;

import com.tourist.enums.Bank;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Beneficiary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idB;
    private String name;
    private Long rib;
    @Enumerated(EnumType.STRING)
    @Column(name = "bank", nullable = false, length = 225)
    private Bank bank;

    private Double sold;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;


    @OneToMany(mappedBy = "beneficiary", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Transaction> transactions;
   
}
