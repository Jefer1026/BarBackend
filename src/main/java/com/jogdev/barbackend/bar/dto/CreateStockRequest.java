package com.jogdev.barbackend.bar.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateStockRequest {
    private int productId;
    private String locationCode;
    private Integer quantity;
    private Integer minQuantity;
}
