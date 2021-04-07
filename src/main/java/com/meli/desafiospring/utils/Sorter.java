package com.meli.desafiospring.utils;

import com.meli.desafiospring.dto.ArticleDTO;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Sorter {
    public static List<ArticleDTO> sortByNameAsc(List<ArticleDTO> articles){
        return articles.stream().sorted(Comparator.comparing(ArticleDTO::getName)).collect(Collectors.toList());
    }

    public static List<ArticleDTO> sortByNameDesc(List<ArticleDTO> articles){
        return articles.stream().sorted(Comparator.comparing(ArticleDTO::getName).reversed()).collect(Collectors.toList());
    }

    public static List<ArticleDTO> sortByPriceAsc(List<ArticleDTO> articles){
        return articles.stream().sorted(Comparator.comparing(ArticleDTO::getPrice)).collect(Collectors.toList());
    }

    public static List<ArticleDTO> sortByPriceDesc(List<ArticleDTO> articles){
        return articles.stream().sorted(Comparator.comparing(ArticleDTO::getPrice).reversed()).collect(Collectors.toList());
    }
}