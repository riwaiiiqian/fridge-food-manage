package com.example.fridge.service;

import com.example.fridge.mapper.FoodItemMapper;
import com.example.fridge.vo.DashboardVO;
import com.example.fridge.vo.FoodItemVO;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final FoodItemMapper foodItemMapper;
    private final FoodStatusService statusService;

    public DashboardService(FoodItemMapper foodItemMapper, FoodStatusService statusService) {
        this.foodItemMapper = foodItemMapper;
        this.statusService = statusService;
    }

    public DashboardVO dashboard() {
        LocalDate today = LocalDate.now();
        LocalDate thresholdDate = statusService.getThresholdDate();

        int total = foodItemMapper.countAll();
        int expired = foodItemMapper.countExpired(today);
        int expiringSoon = foodItemMapper.countExpiringSoon(today, thresholdDate);
        List<FoodItemVO> expiringSoonItems = foodItemMapper.selectExpiringSoonItems(today, thresholdDate, 6);
        statusService.fillStatus(expiringSoonItems);

        DashboardVO dashboard = new DashboardVO();
        dashboard.setTotalCount(total);
        dashboard.setExpiredCount(expired);
        dashboard.setExpiringSoonCount(expiringSoon);
        dashboard.setNormalCount(total - expired - expiringSoon);
        dashboard.setCategoryStats(foodItemMapper.selectCategoryStats());
        dashboard.setExpiringSoonItems(expiringSoonItems);
        return dashboard;
    }
}
