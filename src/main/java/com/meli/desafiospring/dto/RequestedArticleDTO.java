package com.meli.desafiospring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestedArticleDTO {
    private int productId;
    private String name;
    private String brand;
    private int quantity;
}
