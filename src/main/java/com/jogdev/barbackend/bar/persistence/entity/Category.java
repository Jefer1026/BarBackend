package com.jogdev.barbackend.bar.persistence.entity;


import com.jogdev.barbackend.util.StatusObject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "category_name")
    private String categoryName;

    @Enumerated(EnumType.STRING)
    private StatusObject categoryStatus;
}
