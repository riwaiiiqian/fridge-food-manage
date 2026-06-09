package com.example.fridge.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequest {

    @NotBlank(message = "分类名称不能为空")
    private String name;

    private String color = "#3f8cff";
    private Integer sortOrder = 0;
}
