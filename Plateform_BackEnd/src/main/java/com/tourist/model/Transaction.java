package com.tourist.model;

import com.tourist.enums.TypeC;
import com.tourist.enums.TypeTransaction;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idT;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime date;

    private Double amount;
    private Long ribT;

    @Enumerated(EnumType.STRING)
    @Column(name = "typeT", nullable = false, length = 225)
    private TypeTransaction typeT;

    @Enumerated(EnumType.STRING)
    @Column(name = "typeCard", nullable = false, length = 225)
    private TypeC typeCard;

    private String description;

    @ManyToOne
    @JoinColumn(name = "from_account_id")
    @JsonProperty("from_account")
    private Account fromAccount;

    @ManyToOne
    @JoinColumn(name = "to_account_id")
    @JsonProperty("to_account")

    private Account toAccount;

    @ManyToOne
    @JoinColumn(name = "beneficiary_id")
    private Beneficiary beneficiary;

    public Transaction(Account fromAccount, Account toAccount, Double amount, String description) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.description = description;
        this.date = LocalDateTime.now();
    }
}
