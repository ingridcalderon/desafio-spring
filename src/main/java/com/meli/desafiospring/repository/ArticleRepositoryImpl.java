package com.meli.desafiospring.repository;

import com.meli.desafiospring.dto.ArticleDTO;
import com.meli.desafiospring.model.FilterEnum;
import lombok.Data;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Data
public class ArticleRepositoryImpl implements ArticleRepository {
    public static final String SEPARATOR = ",";
    private final List<ArticleDTO> database;

    public ArticleRepositoryImpl() {
        this.database = loadDataBase();
    }

    private List<ArticleDTO> loadDataBase() {
        BufferedReader readBuffer = null;
        List<ArticleDTO> articles = new ArrayList<ArticleDTO>();

        try {
            File file = ResourceUtils.getFile("classpath:dbProductos.csv");
            readBuffer = new BufferedReader(new FileReader(file));

            String line = readBuffer.readLine();
            boolean header = true;

            while (line != null) {
                String[] dataLine = line.split(SEPARATOR);
                List<String> fixedLenghtList = Arrays.asList(dataLine);
                ArrayList<String> listOfString = new ArrayList<String>(fixedLenghtList);

                if(!header){
                    ArticleDTO articleAux = new ArticleDTO(listOfString.get(0),listOfString.get(1),listOfString.get(2),listOfString.get(3),listOfString.get(4),listOfString.get(5),listOfString.get(6));
                    articles.add(articleAux);
                }

                header = false;
                line = readBuffer.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (readBuffer != null) {
                try {
                    readBuffer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return articles;
    }

    @Override
    public List<ArticleDTO> getAllArticles() {
        return this.getDatabase();
    }

    @Override
    public List<ArticleDTO> getArticlesByCategories(Map<String, String> filters) {
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
    public List<ArticleDTO> getArticlesByName(String name, List<ArticleDTO> articlesAux) {
        return articlesAux.stream()
                .filter(a -> a.getName().equals(name))
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDTO> getArticlesByCategory(String category, List<ArticleDTO> articlesAux) {
        return articlesAux.stream()
                .filter(a -> a.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDTO> getArticlesByBrand(String brand, List<ArticleDTO> articlesAux) {
        return articlesAux.stream()
                .filter(a -> a.getBrand().equals(brand))
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDTO> getArticlesByFreeShipping(boolean isFreeShipping, List<ArticleDTO> articlesAux) {
        return articlesAux.stream()
                .filter(a -> a.isFreeShipping() == isFreeShipping)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDTO> getArticlesByPrestige(int prestige, List<ArticleDTO> articlesAux) {
        return articlesAux.stream()
                .filter(a -> a.getPrestige() == prestige)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDTO> getArticlesByPrice(float price, List<ArticleDTO> articlesAux) {
        return articlesAux.stream()
                .filter(a -> a.getPrice() == price)
                .collect(Collectors.toList());
    }
}
