package com.tourist.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelFilterDTO {
    private Long minRating;
    private Long maxRating;


}
