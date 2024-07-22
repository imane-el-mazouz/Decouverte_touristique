package com.tourist.model;

import com.tourist.enums.*;
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
public class Card {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idC;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime expirationDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "typeCard", nullable = false, length = 225)
  private TypeC typeCard;


  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 225)
  private Status status;

  @Enumerated(EnumType.STRING)
  @Column(name = "blockingReason", nullable = false, length = 225)
  private Reason blockingReason;

//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account account;

  public Card(String number, String johnDoe, String s) {
  }


}
