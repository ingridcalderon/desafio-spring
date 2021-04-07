package com.meli.desafiospring.service;

import com.meli.desafiospring.dto.ArticleDTO;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    List<ArticleDTO> getArticles(Map<String, String> queryParams);
}
