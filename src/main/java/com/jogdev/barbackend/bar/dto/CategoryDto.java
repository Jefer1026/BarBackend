package com.jogdev.barbackend.bar.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;


@Getter
@Setter
public class CategoryDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -90963327046701662L;
    private String categoryName;
}
