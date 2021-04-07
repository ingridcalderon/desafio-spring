package com.meli.desafiospring.service;

import com.meli.desafiospring.dto.ArticleDTO;

import java.util.List;

public interface ArticleService {
    List<ArticleDTO> getAllArticles();

    List<ArticleDTO> getArticlesByCategory(String category);

}
