package com.jogdev.barbackend.bar.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class StockTransferRequest {

    private int productId;
    private String fromLocation;
    private String toLocation;
    private int quantity;
}
