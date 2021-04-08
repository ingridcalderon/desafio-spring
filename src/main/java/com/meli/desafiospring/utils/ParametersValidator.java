package com.meli.desafiospring.utils;

import com.meli.desafiospring.exception.SortNumberInvalidException;
import com.meli.desafiospring.enums.FilterEnum;
import com.meli.desafiospring.enums.SortEnum;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ParametersValidator {

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

    private static boolean validFilter(String filter){
        return filter.equals(FilterEnum.NAME.getDescription()) ||
                filter.equals(FilterEnum.CATEGORY.getDescription()) ||
                filter.equals(FilterEnum.BRAND.getDescription()) ||
                filter.equals(FilterEnum.FREE_SHIPPING.getDescription()) ||
                filter.equals(FilterEnum.PRESTIGE.getDescription()) ||
                filter.equals(FilterEnum.PRICE.getDescription());
    }

    private static boolean isValidSorter(Map<String, String> queryParams){
        return queryParams.keySet().contains("order") && isNumberValid(queryParams);
    }

    private static boolean isNumberValid(Map<String, String> queryParams){
        return Integer.parseInt(queryParams.get("order")) == SortEnum.NAME_ASC.getOrder() ||
                Integer.parseInt(queryParams.get("order")) == SortEnum.NAME_DES.getOrder() ||
                Integer.parseInt(queryParams.get("order")) == SortEnum.PRICE_ASC.getOrder() ||
                Integer.parseInt(queryParams.get("order")) == SortEnum.PRICE_DES.getOrder();
    }

    public static int ordering(Map<String, String> queryParams) throws SortNumberInvalidException {
        int orderType = 0;
        if(isValidSorter(queryParams)) {
            orderType = Integer.parseInt(queryParams.get("order"));
            queryParams.remove("order");
        } else {
            if(queryParams.keySet().contains("order")){
                throw new SortNumberInvalidException("Ordenamiento inválido");
            }
        }
        return orderType;
    }
}
