package com.tourist.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AdminDTO {

    private Long totalClients ;
    private Long totalEvents ;
    private Long totalReservations ;

}
