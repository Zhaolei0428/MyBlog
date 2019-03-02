package com.zhao.blog.dto;

import lombok.Data;

@Data
public class BlogResult<T> {
    private boolean result;

    private T data;

    private String error;

    public BlogResult(boolean result, T data) {
        this.result = result;
        this.data = data;
    }

    public BlogResult(boolean result, String error) {
        this.result = result;
        this.error = error;
    }
}
