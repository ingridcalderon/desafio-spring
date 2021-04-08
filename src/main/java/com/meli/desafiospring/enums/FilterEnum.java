package com.meli.desafiospring.enums;

public enum FilterEnum {
    NAME("name"),
    CATEGORY("category"),
    BRAND("brand"),
    FREE_SHIPPING("freeShipping"),
    PRESTIGE("prestige"),
    PRICE("price");

    private String description;

    FilterEnum(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
