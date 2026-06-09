package com.example.fridge.vo;

import java.util.List;
import lombok.Data;

@Data
public class DashboardVO {

    private Integer totalCount;
    private Integer normalCount;
    private Integer expiringSoonCount;
    private Integer expiredCount;
    private List<CategoryStatVO> categoryStats;
    private List<FoodItemVO> expiringSoonItems;
}
