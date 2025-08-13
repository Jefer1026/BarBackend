package com.jogdev.barbackend.bar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor

public class ResponseStock {

    private int id;
    private int quantity;
    private int minQuantity;
    private String creationDate;
    private String lastUpdate;
    private String location;

    private int productId;
    private String productName;
    private BigDecimal productPrice;
}
