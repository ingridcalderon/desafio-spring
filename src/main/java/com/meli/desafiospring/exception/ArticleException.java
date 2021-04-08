package com.meli.desafiospring.exception;

import lombok.Data;

@Data
public class ArticleException extends Exception {
    private int code;
    private String message;

    public ArticleException(int code, String msg) {
        this.code = code;
        this.message = msg;
    }
}