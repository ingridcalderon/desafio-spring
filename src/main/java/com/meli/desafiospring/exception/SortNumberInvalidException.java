package com.meli.desafiospring.exception;

public class SortNumberInvalidException extends ArticleException {

    public SortNumberInvalidException(String msg) {
        super(404, msg);
    }

}
