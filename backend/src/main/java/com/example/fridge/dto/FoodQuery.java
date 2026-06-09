package com.example.fridge.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class FoodQuery {

    private String keyword;
    private Long categoryId;
    private String status;
    private Integer page = 1;
    private Integer pageSize = 20;
    private LocalDate today;
    private LocalDate thresholdDate;

    public int getOffset() {
        int safePage = page == null || page < 1 ? 1 : page;
        int safePageSize = pageSize == null || pageSize < 1 ? 20 : pageSize;
        return (safePage - 1) * safePageSize;
    }

    public int getLimit() {
        return pageSize == null || pageSize < 1 ? 20 : pageSize;
    }
}
