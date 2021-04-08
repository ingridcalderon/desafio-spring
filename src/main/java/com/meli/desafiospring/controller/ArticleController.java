package com.meli.desafiospring.controller;

import com.meli.desafiospring.dto.PurchaseRequestDTO;
import com.meli.desafiospring.dto.StatusCodeDTO;
import com.meli.desafiospring.exception.ArticleException;
import com.meli.desafiospring.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @GetMapping("/articles")
    public ResponseEntity getArticles(@RequestParam Map<String, String> queryParams) throws ArticleException {
        return ResponseEntity.ok(articleService.getArticles(queryParams));
    }

    @PostMapping("/purchase-request")
    public ResponseEntity sendPurchaseRequest(@RequestBody PurchaseRequestDTO purchaseRequest) throws Exception {
        return ResponseEntity.ok(articleService.sendPurchaseRequest(purchaseRequest));
    }

    @ExceptionHandler(ArticleException.class)
    public ResponseEntity<StatusCodeDTO> handlerException(ArticleException e){
        StatusCodeDTO statusDTO = new StatusCodeDTO(e.getCode(), e.getMessage());
        return new ResponseEntity(statusDTO, HttpStatus.valueOf(e.getCode()));
    }
}