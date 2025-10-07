package com.jogdev.barbackend.bar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MyPageable {
    private int offset;
    private int limit;
}
