package com.meli.desafiospring.repository;

import com.meli.desafiospring.dto.*;
import com.meli.desafiospring.enums.FilterEnum;
import com.meli.desafiospring.exception.NoFoundArticlesException;
import com.meli.desafiospring.exception.PurchaseCannotFinalizedException;
import com.meli.desafiospring.utils.CvsReader;
import com.meli.desafiospring.utils.CvsWriter;
import com.meli.desafiospring.utils.Sorter;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@Data
public class ArticleRepositoryImpl implements ArticleRepository {
    private final List<ArticleDTO> database;

    public ArticleRepositoryImpl() {
        this.database = CvsReader.loadDataBase();
    }

    @Override
    public List<ArticleDTO> getAllArticles() throws NoFoundArticlesException {
        List<ArticleDTO> articles = this.getDatabase();
        if(articles.size() == 0){
            throw new NoFoundArticlesException("No se encontraron productos");
        }
        return articles;
    }

    @Override
    public List<ArticleDTO> getArticlesByCategories(Map<String, String> filters) throws NoFoundArticlesException {
        List<ArticleDTO> articlesAux = this.getDatabase();

        for(Map.Entry<String,String> filter : filters.entrySet()){
            if(filter.getKey().equals(FilterEnum.NAME.getDescription())){ articlesAux = this.getArticlesByName(filter.getValue(), articlesAux); };
            if(filter.getKey().equals(FilterEnum.CATEGORY.getDescription())){ articlesAux = this.getArticlesByCategory(filter.getValue(), articlesAux); };
            if(filter.getKey().equals(FilterEnum.BRAND.getDescription())){ articlesAux = this.getArticlesByBrand(filter.getValue(), articlesAux); };
            if(filter.getKey().equals(FilterEnum.FREE_SHIPPING.getDescription())){ articlesAux = this.getArticlesByFreeShipping(Boolean.valueOf(filter.getValue()), articlesAux); };
            if(filter.getKey().equals(FilterEnum.PRESTIGE.getDescription())) { articlesAux = this.getArticlesByPrestige(Integer.valueOf(filter.getValue()), articlesAux);}
            if(filter.getKey().equals(FilterEnum.PRICE.getDescription())){ articlesAux = this.getArticlesByPrice(Float.valueOf(filter.getValue()), articlesAux); };
        }
        return articlesAux;
    }

    @Override
    public List<ArticleDTO> getArticlesByName(String name, List<ArticleDTO> articlesAux) throws NoFoundArticlesException {
        List<ArticleDTO> articles = articlesAux.stream()
                .filter(a -> a.getName().equals(name))
                .collect(Collectors.toList());
        if(articles.size() == 0){
            throw new NoFoundArticlesException("No se encontraron artículos con el nombre: " + name);
        }
        return articles;
    }

    @Override
    public List<ArticleDTO> getArticlesByCategory(String category, List<ArticleDTO> articlesAux) throws NoFoundArticlesException {
        List<ArticleDTO> articles = articlesAux.stream()
                .filter(a -> a.getCategory().equals(category))
                .collect(Collectors.toList());
        if(articles.size() == 0){
            throw new NoFoundArticlesException("No se encontraron artículos con la categoría: " + category);
        }
        return articles;
    }

    @Override
    public List<ArticleDTO> getArticlesByBrand(String brand, List<ArticleDTO> articlesAux) throws NoFoundArticlesException {
        List<ArticleDTO> articles = articlesAux.stream()
                .filter(a -> a.getBrand().equals(brand))
                .collect(Collectors.toList());
        if(articles.size() == 0){
            throw new NoFoundArticlesException("No se encontraron artículos con la marca: " + brand);
        }
        return articles;
    }

    @Override
    public List<ArticleDTO> getArticlesByFreeShipping(boolean isFreeShipping, List<ArticleDTO> articlesAux) throws NoFoundArticlesException {
        List<ArticleDTO> articles = articlesAux.stream()
                .filter(a -> a.isFreeShipping() == isFreeShipping)
                .collect(Collectors.toList());
        if(articles.size() == 0){
            throw new NoFoundArticlesException("No se encontraron artículos con envío gratis");
        }
        return articles;
    }

    @Override
    public List<ArticleDTO> getArticlesByPrestige(int prestige, List<ArticleDTO> articlesAux) throws NoFoundArticlesException {
        List<ArticleDTO> articles = articlesAux.stream()
                .filter(a -> a.getPrestige() == prestige)
                .collect(Collectors.toList());
        if(articles.size() == 0){
            throw new NoFoundArticlesException("No se encontraron artículos con " + prestige + " estrellas");
        }
        return articles;
    }

    @Override
    public List<ArticleDTO> getArticlesByPrice(float price, List<ArticleDTO> articlesAux) throws NoFoundArticlesException {
        List<ArticleDTO> articles = articlesAux.stream()
                .filter(a -> a.getPrice() == price)
                .collect(Collectors.toList());
        if(articles.size() == 0){
            throw new NoFoundArticlesException("No se encontraron artículos con precio: " + price);
        }
        return articles;
    }

    @Override
    public List<ArticleDTO> sortArticles(int orderType, List<ArticleDTO> articles){
        switch (orderType){
            case 0:
                return Sorter.sortByNameAsc(articles);
            case 1:
                return Sorter.sortByNameDesc(articles);
            case 2:
                return Sorter.sortByPriceDesc(articles);
            case 3:
                return Sorter.sortByPriceAsc(articles);
        }
        return null;
    }

    @Override
    public PurchaseResponseDTO sendPurchaseRequest(PurchaseRequestDTO purchaseRequest) throws Exception {
        List<ArticleDTO> articles = CvsReader.loadDataBase();
        List<RequestedArticleDTO> articlePurchase = purchaseRequest.getArticles();

        boolean canSendPurchase = true;
        for(RequestedArticleDTO article : articlePurchase){
            if(canSendPurchase) {
                canSendPurchase = this.stockOfArticle(articles, article);
            }
        }

        //Generar ticket
        TicketDTO ticket = null;
        if(canSendPurchase){
            ticket = this.generateTicket(purchaseRequest);
            //TODO update cvs
            CvsWriter.updateCsvFile(purchaseRequest);

        } else {
            throw new PurchaseCannotFinalizedException("No se puede realizar la compra por falta de stock");
        }

        StatusCodeDTO status = new StatusCodeDTO(200, "La solicitud de compra se completó con éxito");
        PurchaseResponseDTO responseDTO = new PurchaseResponseDTO(ticket, status);

        return responseDTO;
    }

    private TicketDTO generateTicket(PurchaseRequestDTO purchaseRequest) {
        float prices = 0;
        for(int i = 0; i < purchaseRequest.getArticles().size(); i++){
            final int x = i;
            Optional<ArticleDTO> articleAux = this.getDatabase().stream().filter(a -> a.getProductId() == purchaseRequest.getArticles().get(x).getProductId()).findFirst();
            prices += articleAux.get().getPrice();
        }

        TicketDTO ticket = new TicketDTO(purchaseRequest.getArticles(), prices);
        return ticket;
    }

    private boolean stockOfArticle(List<ArticleDTO> articles, RequestedArticleDTO article){
        boolean existsArticle = articles.stream().filter(a -> article.getProductId() == a.getProductId()).count() > 0;

        if(existsArticle){
            return this.checkStock(articles, article);
        }
        return false;
    }

    private boolean checkStock(List<ArticleDTO> articles, RequestedArticleDTO article){
        Optional<ArticleDTO> articleAux = articles.stream().filter(a -> a.getProductId() == article.getProductId()).findFirst();
        ArticleDTO a = null;

        if(articleAux.isPresent()){
            a = articleAux.get();
        }
        return a.getQuantity() >= article.getQuantity();
    }
}
