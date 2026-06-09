package com.example.fridge.vo;

import lombok.Data;

@Data
public class CategoryStatVO {

    private Long categoryId;
    private String categoryName;
    private String categoryColor;
    private Integer count;
}
