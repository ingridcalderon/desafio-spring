package com.meli.desafiospring.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArticleDTO {
    private int productId;
    private String name;
    private String category;
    private String brand;
    private float price;
    private int quantity;
    private boolean freeShipping;
    private int prestige;

    public ArticleDTO(String productId, String name, String category, String brand, String price,
                      String quantity, String freeShipping, String prestige) {
        this.productId = Integer.parseInt(productId);
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.price = Float.parseFloat(price.substring(1).replaceAll("\\.",""));
        this.quantity = Integer.parseInt(quantity);
        this.freeShipping = freeShipping.equals("SI") ? true : false;
        this.prestige = prestige.length();
    }
}
