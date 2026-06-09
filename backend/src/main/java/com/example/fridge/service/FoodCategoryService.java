package com.example.fridge.service;

import com.example.fridge.dto.CategoryRequest;
import com.example.fridge.entity.FoodCategory;
import com.example.fridge.mapper.FoodCategoryMapper;
import com.example.fridge.vo.FoodCategoryVO;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FoodCategoryService {

    private final FoodCategoryMapper categoryMapper;

    public FoodCategoryService(FoodCategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    public List<FoodCategoryVO> list() {
        return categoryMapper.selectAllWithCount();
    }

    @Transactional
    public FoodCategory create(CategoryRequest request) {
        ensureNameAvailable(request.getName(), null);
        FoodCategory category = new FoodCategory();
        category.setName(request.getName().trim());
        category.setColor(defaultIfBlank(request.getColor(), "#3f8cff"));
        category.setSortOrder(request.getSortOrder() == null ? 0 : request.getSortOrder());
        categoryMapper.insert(category);
        return category;
    }

    @Transactional
    public FoodCategory update(Long id, CategoryRequest request) {
        FoodCategory category = categoryMapper.selectById(id);
        if (category == null) {
            throw new IllegalArgumentException("分类不存在");
        }
        ensureNameAvailable(request.getName(), id);
        category.setName(request.getName().trim());
        category.setColor(defaultIfBlank(request.getColor(), "#3f8cff"));
        category.setSortOrder(request.getSortOrder() == null ? 0 : request.getSortOrder());
        categoryMapper.update(category);
        return category;
    }

    @Transactional
    public void delete(Long id) {
        FoodCategory category = categoryMapper.selectById(id);
        if (category == null) {
            throw new IllegalArgumentException("分类不存在");
        }
        if (categoryMapper.countItems(id) > 0) {
            throw new IllegalArgumentException("该分类下还有食材，不能删除");
        }
        categoryMapper.deleteById(id);
    }

    private void ensureNameAvailable(String name, Long currentId) {
        FoodCategory exists = categoryMapper.selectByName(name.trim());
        if (exists != null && (currentId == null || !exists.getId().equals(currentId))) {
            throw new IllegalArgumentException("分类名称已存在");
        }
    }

    private String defaultIfBlank(String value, String defaultValue) {
        return value == null || value.trim().isEmpty() ? defaultValue : value.trim();
    }
}
