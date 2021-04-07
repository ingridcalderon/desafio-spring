package com.meli.desafiospring.controller;

import com.meli.desafiospring.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @GetMapping("/articles")
    public ResponseEntity getAllArticles() {
        return ResponseEntity.ok(articleService.getAllArticles());
    }

    @GetMapping(value = "/articles", params = {"category"})
    public ResponseEntity getArticlesByCategory(@RequestParam(name = "category") String category) {
        return ResponseEntity.ok(articleService.getArticlesByCategory(category));
    }
}