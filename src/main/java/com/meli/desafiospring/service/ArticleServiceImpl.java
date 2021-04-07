package com.meli.desafiospring.service;

import com.meli.desafiospring.dto.ArticleDTO;
import com.meli.desafiospring.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    @Override
    public List<ArticleDTO> getAllArticles() {
        return articleRepository.getAllArticles();
    }

    @Override
    public List<ArticleDTO> getArticlesByCategory(String category) {
        return articleRepository.getArticlesByCategory(category);
    }
}
