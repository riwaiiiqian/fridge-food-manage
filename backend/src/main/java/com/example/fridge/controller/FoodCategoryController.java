package com.example.fridge.controller;

import com.example.fridge.common.ApiResponse;
import com.example.fridge.dto.CategoryRequest;
import com.example.fridge.entity.FoodCategory;
import com.example.fridge.service.FoodCategoryService;
import com.example.fridge.vo.FoodCategoryVO;
import java.util.List;
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
@RequestMapping("/api/categories")
public class FoodCategoryController {

    private final FoodCategoryService categoryService;

    public FoodCategoryController(FoodCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ApiResponse<List<FoodCategoryVO>> list() {
        return ApiResponse.ok(categoryService.list());
    }

    @PostMapping
    public ApiResponse<FoodCategory> create(@Valid @RequestBody CategoryRequest request) {
        return ApiResponse.ok("分类创建成功", categoryService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<FoodCategory> update(@PathVariable Long id, @Valid @RequestBody CategoryRequest request) {
        return ApiResponse.ok("分类更新成功", categoryService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ApiResponse.ok("分类删除成功", null);
    }
}
