package com.example.fridge.mapper;

import com.example.fridge.dto.FoodQuery;
import com.example.fridge.entity.FoodItem;
import com.example.fridge.vo.CategoryStatVO;
import com.example.fridge.vo.FoodItemVO;
import java.time.LocalDate;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FoodItemMapper {

    List<FoodItemVO> selectPage(@Param("query") FoodQuery query);

    long count(@Param("query") FoodQuery query);

    FoodItemVO selectVOById(@Param("id") Long id);

    FoodItem selectById(@Param("id") Long id);

    int insert(FoodItem foodItem);

    int update(FoodItem foodItem);

    int deleteById(@Param("id") Long id);

    int countAll();

    int countExpired(@Param("today") LocalDate today);

    int countExpiringSoon(@Param("today") LocalDate today, @Param("thresholdDate") LocalDate thresholdDate);

    List<CategoryStatVO> selectCategoryStats();

    List<FoodItemVO> selectExpiringSoonItems(@Param("today") LocalDate today,
                                             @Param("thresholdDate") LocalDate thresholdDate,
                                             @Param("limit") Integer limit);
}
