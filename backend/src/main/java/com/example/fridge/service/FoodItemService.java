package com.example.fridge.service;

import com.example.fridge.common.PageResult;
import com.example.fridge.dto.FoodItemRequest;
import com.example.fridge.dto.FoodQuery;
import com.example.fridge.entity.FoodItem;
import com.example.fridge.mapper.FoodCategoryMapper;
import com.example.fridge.mapper.FoodItemMapper;
import com.example.fridge.vo.FoodItemVO;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FoodItemService {

    private final FoodItemMapper foodItemMapper;
    private final FoodCategoryMapper categoryMapper;
    private final FoodStatusService statusService;

    public FoodItemService(FoodItemMapper foodItemMapper,
                           FoodCategoryMapper categoryMapper,
                           FoodStatusService statusService) {
        this.foodItemMapper = foodItemMapper;
        this.categoryMapper = categoryMapper;
        this.statusService = statusService;
    }

    public PageResult<FoodItemVO> page(FoodQuery query) {
        normalizeQuery(query);
        List<FoodItemVO> records = foodItemMapper.selectPage(query);
        statusService.fillStatus(records);
        long total = foodItemMapper.count(query);
        return new PageResult<FoodItemVO>(records, total, query.getPage(), query.getPageSize());
    }

    public FoodItemVO detail(Long id) {
        FoodItemVO foodItem = foodItemMapper.selectVOById(id);
        if (foodItem == null) {
            throw new IllegalArgumentException("食材不存在");
        }
        statusService.fillStatus(foodItem);
        return foodItem;
    }

    @Transactional
    public FoodItem create(FoodItemRequest request) {
        ensureCategoryExists(request.getCategoryId());
        FoodItem foodItem = toEntity(request);
        foodItemMapper.insert(foodItem);
        return foodItem;
    }

    @Transactional
    public FoodItem update(Long id, FoodItemRequest request) {
        FoodItem exists = foodItemMapper.selectById(id);
        if (exists == null) {
            throw new IllegalArgumentException("食材不存在");
        }
        ensureCategoryExists(request.getCategoryId());
        FoodItem foodItem = toEntity(request);
        foodItem.setId(id);
        foodItemMapper.update(foodItem);
        return foodItem;
    }

    @Transactional
    public void delete(Long id) {
        FoodItem exists = foodItemMapper.selectById(id);
        if (exists == null) {
            throw new IllegalArgumentException("食材不存在");
        }
        foodItemMapper.deleteById(id);
    }

    private FoodItem toEntity(FoodItemRequest request) {
        FoodItem foodItem = new FoodItem();
        foodItem.setName(request.getName().trim());
        foodItem.setCategoryId(request.getCategoryId());
        foodItem.setQuantity(request.getQuantity());
        foodItem.setUnit(request.getUnit().trim());
        foodItem.setProductionDate(request.getProductionDate());
        foodItem.setShelfLifeDays(request.getShelfLifeDays());
        foodItem.setExpireDate(resolveExpireDate(request));
        foodItem.setLocation(request.getLocation().trim());
        foodItem.setRemark(request.getRemark());
        return foodItem;
    }

    private LocalDate resolveExpireDate(FoodItemRequest request) {
        if (request.getExpireDate() != null) {
            return request.getExpireDate();
        }
        if (request.getProductionDate() != null && request.getShelfLifeDays() != null) {
            return request.getProductionDate().plusDays(request.getShelfLifeDays());
        }
        throw new IllegalArgumentException("请填写到期日期");
    }

    private void ensureCategoryExists(Long categoryId) {
        if (categoryMapper.selectById(categoryId) == null) {
            throw new IllegalArgumentException("分类不存在");
        }
    }

    private void normalizeQuery(FoodQuery query) {
        if (query.getPage() == null || query.getPage() < 1) {
            query.setPage(1);
        }
        if (query.getPageSize() == null || query.getPageSize() < 1) {
            query.setPageSize(20);
        }
        if (query.getPageSize() > 100) {
            query.setPageSize(100);
        }
        if (query.getKeyword() != null) {
            query.setKeyword(query.getKeyword().trim());
        }
        query.setToday(LocalDate.now());
        query.setThresholdDate(statusService.getThresholdDate());
    }
}
