package com.meli.desafiospring.exception;

public class NoFoundArticlesException extends ArticleException {

    public NoFoundArticlesException(String msg) {
        super(404, msg);
    }
}