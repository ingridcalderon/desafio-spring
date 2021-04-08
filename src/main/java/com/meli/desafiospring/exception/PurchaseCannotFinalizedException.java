package com.meli.desafiospring.exception;

public class PurchaseCannotFinalizedException extends ArticleException{
    public PurchaseCannotFinalizedException(String msg){
        super(409, msg);
    }
}
