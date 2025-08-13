package com.jogdev.barbackend.bar.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor

public class ProductWithStockDto {

    private String productName;
    private BigDecimal productPrice;
    private int categoryId;
    private Integer stockQuantity;
    private Integer stockMinimum;
    private LocalDateTime lastUpdate;
}
