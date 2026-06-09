package com.example.fridge.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class FoodItem {

    private Long id;
    private String name;
    private Long categoryId;
    private BigDecimal quantity;
    private String unit;
    private LocalDate productionDate;
    private Integer shelfLifeDays;
    private LocalDate expireDate;
    private String location;
    private String remark;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
