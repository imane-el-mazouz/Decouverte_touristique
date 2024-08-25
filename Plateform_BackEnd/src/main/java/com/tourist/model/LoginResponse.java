package com.tourist.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    @Id
    private Long id;
    @Column(unique = true)

    String email ;
    String password ;




}
