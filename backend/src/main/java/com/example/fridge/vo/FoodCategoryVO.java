package com.example.fridge.vo;

import lombok.Data;

@Data
public class FoodCategoryVO {

    private Long id;
    private String name;
    private String color;
    private Integer sortOrder;
    private Integer itemCount;
}
