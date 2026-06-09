package com.example.fridge.controller;

import com.example.fridge.common.ApiResponse;
import com.example.fridge.service.DashboardService;
import com.example.fridge.vo.DashboardVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ApiResponse<DashboardVO> dashboard() {
        return ApiResponse.ok(dashboardService.dashboard());
    }
}
