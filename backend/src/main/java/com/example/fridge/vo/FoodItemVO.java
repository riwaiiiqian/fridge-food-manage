package com.example.fridge.vo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class FoodItemVO {

    private Long id;
    private String name;
    private Long categoryId;
    private String categoryName;
    private String categoryColor;
    private BigDecimal quantity;
    private String unit;
    private LocalDate productionDate;
    private Integer shelfLifeDays;
    private LocalDate expireDate;
    private String location;
    private String remark;
    private String status;
    private String statusText;
    private Long daysLeft;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
