package com.example.fridge.controller;

import com.example.fridge.common.ApiResponse;
import com.example.fridge.common.PageResult;
import com.example.fridge.dto.FoodItemRequest;
import com.example.fridge.dto.FoodQuery;
import com.example.fridge.entity.FoodItem;
import com.example.fridge.service.FoodItemService;
import com.example.fridge.vo.FoodItemVO;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/foods")
public class FoodItemController {

    private final FoodItemService foodItemService;

    public FoodItemController(FoodItemService foodItemService) {
        this.foodItemService = foodItemService;
    }

    @GetMapping
    public ApiResponse<PageResult<FoodItemVO>> page(FoodQuery query) {
        return ApiResponse.ok(foodItemService.page(query));
    }

    @GetMapping("/{id}")
    public ApiResponse<FoodItemVO> detail(@PathVariable Long id) {
        return ApiResponse.ok(foodItemService.detail(id));
    }

    @PostMapping
    public ApiResponse<FoodItem> create(@Valid @RequestBody FoodItemRequest request) {
        return ApiResponse.ok("食材创建成功", foodItemService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<FoodItem> update(@PathVariable Long id, @Valid @RequestBody FoodItemRequest request) {
        return ApiResponse.ok("食材更新成功", foodItemService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        foodItemService.delete(id);
        return ApiResponse.ok("食材删除成功", null);
    }
}
