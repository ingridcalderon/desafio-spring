package com.meli.desafiospring.service;

import com.meli.desafiospring.dto.ArticleDTO;
import com.meli.desafiospring.dto.PurchaseRequestDTO;
import com.meli.desafiospring.dto.PurchaseResponseDTO;
import com.meli.desafiospring.exception.ArticleException;
import com.meli.desafiospring.exception.ParametersFilterInvalidException;
import com.meli.desafiospring.repository.ArticleRepository;
import com.meli.desafiospring.utils.ParametersValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    @Override
    public List<ArticleDTO> getArticles(Map<String, String> queryParams) throws ArticleException {
        List<ArticleDTO> unorderedArticles = null;
        int ordering = ParametersValidator.ordering(queryParams);
        int quantityParams = queryParams.keySet().size();

        switch (quantityParams) {
            case 0:
                unorderedArticles = articleRepository.getAllArticles();
            case 1:
                if(ParametersValidator.isCategoryFilter(queryParams)){
                    unorderedArticles = articleRepository.getArticlesByCategories(queryParams);
                }
            case 2:
                if(ParametersValidator.validFilters(queryParams)){
                    unorderedArticles = articleRepository.getArticlesByCategories(queryParams);
                }
                break;
            default:
                throw new ParametersFilterInvalidException("Parámetros de filtros inválidos");
        }
        unorderedArticles = this.sort(ordering, unorderedArticles);
        return unorderedArticles;
    }

    @Override
    public PurchaseResponseDTO sendPurchaseRequest(PurchaseRequestDTO purchaseRequest) throws Exception {
        return articleRepository.sendPurchaseRequest(purchaseRequest);
    }

    public List<ArticleDTO> sort(int ordering, List<ArticleDTO> articles){
        return articleRepository.sortArticles(ordering, articles);
    }
}
