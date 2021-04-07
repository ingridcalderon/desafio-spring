package com.meli.desafiospring.repository;

import com.meli.desafiospring.dto.ArticleDTO;

import java.util.List;

public interface ArticleRepository {
    List<ArticleDTO> getAllArticles();

    List<ArticleDTO> getArticlesByCategory(String category);

}
