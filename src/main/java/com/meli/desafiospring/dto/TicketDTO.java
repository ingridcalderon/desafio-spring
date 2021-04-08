package com.meli.desafiospring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TicketDTO {
    private static int count = 0;
    private int id;
    private List<RequestedArticleDTO> articles;
    private float total;

    public TicketDTO(List<RequestedArticleDTO> articles, float total){
        this.articles = articles;
        this.total = total;
        this.count++;
        this.id = this.count;
    }
}
