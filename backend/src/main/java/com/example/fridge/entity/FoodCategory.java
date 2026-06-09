package com.example.fridge.entity;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class FoodCategory {

    private Long id;
    private String name;
    private String color;
    private Integer sortOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
