package com.tourist.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactDTO {
    private Long id;
    private String firstName ;
    private String fullName ;
    private String email ;
    private String message ;
}
