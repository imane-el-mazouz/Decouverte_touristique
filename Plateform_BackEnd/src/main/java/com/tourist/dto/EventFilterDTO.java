package com.tourist.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventFilterDTO {
        private Double minPrice;
        private Double maxPrice;
        private Integer minRating;
        private Integer maxRating;
        private Double maxDistance;

}
