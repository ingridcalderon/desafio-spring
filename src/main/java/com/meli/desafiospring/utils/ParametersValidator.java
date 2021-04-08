package com.meli.desafiospring.utils;

import com.meli.desafiospring.model.FilterEnum;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FilterValidator {

    public static boolean isCategoryFilter(Map<String, String> queryParams){
        return queryParams.keySet().contains(FilterEnum.CATEGORY.getDescription());
    }

    public static boolean validFilters(Map<String, String> queryParams){
        List<String> listParams = queryParams.keySet().stream().collect(Collectors.toList());
        boolean validFilters = true;

        for (String param : listParams){
            if(!validFilter(param)){
                validFilters = false;
            }
        }

        return validFilters;
    }

    public static boolean validFilter(String filter){
        return filter.equals(FilterEnum.NAME.getDescription()) ||
                filter.equals(FilterEnum.CATEGORY.getDescription()) ||
                filter.equals(FilterEnum.BRAND.getDescription()) ||
                filter.equals(FilterEnum.FREE_SHIPPING.getDescription()) ||
                filter.equals(FilterEnum.PRESTIGE.getDescription()) ||
                filter.equals(FilterEnum.PRICE.getDescription());
    }
}
