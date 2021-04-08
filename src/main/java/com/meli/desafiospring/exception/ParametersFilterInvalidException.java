package com.meli.desafiospring.exception;

public class ParametersFilterInvalidException extends ArticleException {

    public ParametersFilterInvalidException(String msg) {
        super(400, msg);
    }
}