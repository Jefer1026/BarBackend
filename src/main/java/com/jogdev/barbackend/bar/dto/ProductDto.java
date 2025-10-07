package com.jogdev.barbackend.bar.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;


@Getter
@Setter

public class ProductDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 3257147140704532036L;


    private String productName;
    private BigDecimal productPrice;
    private BigDecimal  productCost;
    private Integer categoryId;
}
