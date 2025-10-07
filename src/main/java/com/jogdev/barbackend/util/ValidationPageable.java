package com.jogdev.barbackend.util;

public class ValidationPageable {

    public static int createPage(int offset,int limit){
        if (offset < 0) {
            throw new IllegalArgumentException("Offset cannot be negative");
        }
        if (limit <= 0) {
            throw new IllegalArgumentException("Limit must be greater than 0");
        }
        if (limit > 100) {
            throw new IllegalArgumentException("Limit cannot be greater than 100");
        }

        return offset = (offset-1)*limit;
    }
}
