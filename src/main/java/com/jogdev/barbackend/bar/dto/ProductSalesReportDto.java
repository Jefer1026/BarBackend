package com.jogdev.barbackend.bar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProductSalesReportDto {

    private String productName;
    private BigDecimal totalQuantitySold;
    private BigDecimal totalRevenue;

}
