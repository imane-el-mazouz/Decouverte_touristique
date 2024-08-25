package com.tourist.dto;

import com.tourist.model.Tradition;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class BlogDTO {
    private String name;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tradition tradition;
}
