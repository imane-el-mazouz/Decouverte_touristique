package com.tourist.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tourist.model.Blog;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TraditionDTO {
    private Long id;
    private String city;
    private String description;

    @OneToMany(mappedBy = "tradition", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Blog> blogs = new ArrayList<>();
}
