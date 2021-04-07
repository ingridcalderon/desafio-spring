package com.meli.desafiospring.repository;

import com.meli.desafiospring.dto.ArticleDTO;

import java.util.List;
import java.util.Map;

public interface ArticleRepository {
    List<ArticleDTO> getAllArticles();

    List<ArticleDTO> getArticlesByCategories(Map<String, String> queryParams);

    List<ArticleDTO> getArticlesByName(String name, List<ArticleDTO> articlesAux);

    List<ArticleDTO> getArticlesByCategory(String category, List<ArticleDTO> articlesAux);

    List<ArticleDTO> getArticlesByBrand(String brand, List<ArticleDTO> articlesAux);

    List<ArticleDTO> getArticlesByFreeShipping(boolean isFreeShipping, List<ArticleDTO> articlesAux);

    List<ArticleDTO> getArticlesByPrestige(int prestige, List<ArticleDTO> articlesAux);

    List<ArticleDTO> getArticlesByPrice(float price, List<ArticleDTO> articlesAux);

    List<ArticleDTO> sortArticles(int ordering, List<ArticleDTO> articles);
}
