package com.meli.desafiospring.service;

import com.meli.desafiospring.dto.ArticleDTO;
import com.meli.desafiospring.dto.PurchaseRequestDTO;
import com.meli.desafiospring.model.FilterEnum;
import com.meli.desafiospring.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    @Override
    public List<ArticleDTO> getArticles(Map<String, String> queryParams) {
        List<ArticleDTO> unorderedArticles = null;
        int ordering = this.ordering(queryParams);
        int quantityParams = queryParams.keySet().size();

        switch (quantityParams) {
            case 0:
                unorderedArticles = articleRepository.getAllArticles();
            case 1:
                if(this.isCategoryFilter(queryParams)){
                    unorderedArticles = articleRepository.getArticlesByCategories(queryParams);
                }
            case 2:
                if(this.validFilters(queryParams)){
                    unorderedArticles = articleRepository.getArticlesByCategories(queryParams);
                }
        }
        unorderedArticles = this.sort(ordering, unorderedArticles);
        return unorderedArticles;
    }

    public List<ArticleDTO> getArticlesByCategories(Map<String, String> queryParams) {
        return articleRepository.getArticlesByCategories(queryParams);
    }


    //TODO: crear un validador de filtros
    private boolean isCategoryFilter(Map<String, String> queryParams){
        return queryParams.keySet().contains(FilterEnum.CATEGORY.getDescription());
    }

    private boolean validFilters(Map<String, String> queryParams){
        List<String> listParams = queryParams.keySet().stream().collect(Collectors.toList());
        boolean validFilters = true;

        for (String param : listParams){
            if(!this.validFilter(param)){
                validFilters = false;
            }
        }

        return validFilters;
    }

    private boolean validFilter(String filter){
        return filter.equals(FilterEnum.NAME.getDescription()) ||
                filter.equals(FilterEnum.CATEGORY.getDescription()) ||
                filter.equals(FilterEnum.BRAND.getDescription()) ||
                filter.equals(FilterEnum.FREE_SHIPPING.getDescription()) ||
                filter.equals(FilterEnum.PRESTIGE.getDescription()) ||
                filter.equals(FilterEnum.PRICE.getDescription());
    }

    private boolean isSorter(Map<String, String> queryParams){
        return queryParams.keySet().contains("order");
    }

    private int ordering(Map<String, String> queryParams){
        int o = this.isSorter(queryParams) ? Integer.parseInt(queryParams.get("order")) : 100;
        queryParams.remove("order");
        return o;
    }

    private List<ArticleDTO> sort(int ordering, List<ArticleDTO> articles){
        return articleRepository.sortArticles(ordering, articles);
    }
}
