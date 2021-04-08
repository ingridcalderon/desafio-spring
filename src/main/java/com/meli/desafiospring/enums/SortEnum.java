package com.meli.desafiospring.model;

public enum SortEnum {
    NAME_ASC(0),
    NAME_DES(1),
    PRICE_ASC(2),
    PRICE_DES(3);

    private int order;

    SortEnum(int order){
        this.order = order;
    }

    public int getOrder(){
        return order;
    }
}
