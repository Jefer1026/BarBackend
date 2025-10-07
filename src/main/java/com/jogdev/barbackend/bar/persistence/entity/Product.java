package com.jogdev.barbackend.bar.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jogdev.barbackend.util.StatusObject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@Entity

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private BigDecimal productPrice;

    @Column(name = "product_cost")
    private BigDecimal productCost;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_status")
    private StatusObject productStatus;



    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    @JsonBackReference
    private List<Stock> stocks;
}
