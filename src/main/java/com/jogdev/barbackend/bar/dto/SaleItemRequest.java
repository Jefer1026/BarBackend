package com.jogdev.barbackend.bar.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SaleItemRequest {

    private int productId;
    private int quantity;
}
