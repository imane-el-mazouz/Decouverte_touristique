package com.tourist.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Setter
@Getter

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class LoginResponse {
    @Id
    private Long id;

   String name ;
   String password ;
//    @Id
//    private String token;
//    private long expiresIn;



}
