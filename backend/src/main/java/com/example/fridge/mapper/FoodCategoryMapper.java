package com.example.fridge.mapper;

import com.example.fridge.entity.FoodCategory;
import com.example.fridge.vo.FoodCategoryVO;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FoodCategoryMapper {

    List<FoodCategoryVO> selectAllWithCount();

    FoodCategory selectById(@Param("id") Long id);

    FoodCategory selectByName(@Param("name") String name);

    int insert(FoodCategory category);

    int update(FoodCategory category);

    int deleteById(@Param("id") Long id);

    int countItems(@Param("id") Long id);
}
