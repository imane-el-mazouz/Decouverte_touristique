package com.tourist.model;
import com.tourist.enums.Bank;
import com.tourist.enums.TypeA;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity

public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idA;

    @Enumerated(EnumType.STRING)
    @Column(name = "typeA", nullable = false, length = 225)
    private TypeA typeA;

    private Double sold;
    private Long rib;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime date;


    private Boolean accountClosed = false;
    private String closeureReason ;

    @Enumerated(EnumType.STRING)
    @Column(name = "bank", nullable = false, length = 225)
    private Bank bank;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;


    @OneToMany(mappedBy = "account")
    @JsonIgnore
    private List<Beneficiary> beneficiaries;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Card> cards = new ArrayList<>();


//    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<Transaction> transactions ;

    @OneToMany(mappedBy = "fromAccount", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Transaction> outgoingTransactions;

    @OneToMany(mappedBy = "toAccount", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Transaction> incomingTransactions;



//    public List<Transaction> getTransactions() {
//        return getTransactions();
//    }

    public List<Transaction> getTransactions() {
        List<Transaction> allTransactions = new ArrayList<>();
        allTransactions.addAll(outgoingTransactions);
        allTransactions.addAll(incomingTransactions);
        return allTransactions;
    }
}
