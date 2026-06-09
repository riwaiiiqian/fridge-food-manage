package com.example.fridge.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FoodItemRequest {

    @NotBlank(message = "食材名称不能为空")
    private String name;

    @NotNull(message = "分类不能为空")
    private Long categoryId;

    @NotNull(message = "数量不能为空")
    @DecimalMin(value = "0.01", message = "数量必须大于 0")
    private BigDecimal quantity;

    @NotBlank(message = "单位不能为空")
    private String unit;

    private LocalDate productionDate;
    private Integer shelfLifeDays;

    @NotNull(message = "到期日期不能为空")
    private LocalDate expireDate;

    @NotBlank(message = "存放位置不能为空")
    private String location;

    private String remark;
}
