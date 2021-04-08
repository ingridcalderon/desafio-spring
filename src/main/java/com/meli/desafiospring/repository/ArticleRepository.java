package com.meli.desafiospring.repository;

import com.meli.desafiospring.dto.ArticleDTO;
import com.meli.desafiospring.dto.PurchaseRequestDTO;
import com.meli.desafiospring.dto.PurchaseResponseDTO;
import com.meli.desafiospring.exception.NoFoundArticlesException;

import java.util.List;
import java.util.Map;

public interface ArticleRepository {
    List<ArticleDTO> getAllArticles() throws NoFoundArticlesException;

    List<ArticleDTO> getArticlesByCategories(Map<String, String> queryParams) throws NoFoundArticlesException;

    List<ArticleDTO> getArticlesByName(String name, List<ArticleDTO> articlesAux) throws NoFoundArticlesException;

    List<ArticleDTO> getArticlesByCategory(String category, List<ArticleDTO> articlesAux) throws NoFoundArticlesException;

    List<ArticleDTO> getArticlesByBrand(String brand, List<ArticleDTO> articlesAux) throws NoFoundArticlesException;

    List<ArticleDTO> getArticlesByFreeShipping(boolean isFreeShipping, List<ArticleDTO> articlesAux) throws NoFoundArticlesException;

    List<ArticleDTO> getArticlesByPrestige(int prestige, List<ArticleDTO> articlesAux) throws NoFoundArticlesException;

    List<ArticleDTO> getArticlesByPrice(float price, List<ArticleDTO> articlesAux) throws NoFoundArticlesException;

    List<ArticleDTO> sortArticles(int ordering, List<ArticleDTO> articles);

    PurchaseResponseDTO sendPurchaseRequest(PurchaseRequestDTO purchaseRequest) throws Exception;
}
