package com.meli.desafiospring.controller;

import com.meli.desafiospring.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @GetMapping
    public ResponseEntity getArticles(@RequestParam Map<String, String> queryParams) {
        return ResponseEntity.ok(articleService.getArticles(queryParams));
    }

}