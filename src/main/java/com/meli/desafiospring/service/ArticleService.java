package com.meli.desafiospring.service;

import com.meli.desafiospring.dto.ArticleDTO;
import com.meli.desafiospring.dto.PurchaseRequestDTO;
import com.meli.desafiospring.dto.PurchaseResponseDTO;
import com.meli.desafiospring.exception.ArticleException;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    List<ArticleDTO> getArticles(Map<String, String> queryParams) throws ArticleException;

    PurchaseResponseDTO sendPurchaseRequest(PurchaseRequestDTO purchaseRequest) throws Exception;
}
