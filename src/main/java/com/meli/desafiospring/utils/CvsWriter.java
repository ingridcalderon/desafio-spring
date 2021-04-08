package com.meli.desafiospring.utils;

import com.meli.desafiospring.dto.PurchaseRequestDTO;
import com.meli.desafiospring.dto.RequestedArticleDTO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CvsWriter {
    public static String[] HEADERS = { "productId", "name", "category", "brand", "price", "quantity",	"freeShipping",	"prestige"};

    public static void updateCsvFile(PurchaseRequestDTO purchaseRequest) {
        List<RequestedArticleDTO> articles = purchaseRequest.getArticles();

        try {

            File file = ResourceUtils.getFile("classpath:dbProductos.csv");

            Reader in = new FileReader(file);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader(HEADERS)
                    .withFirstRecordAsHeader()
                    .parse(in);
            String edited = file.getAbsolutePath();
            file.delete();
            CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(edited), CSVFormat.DEFAULT.withHeader(HEADERS));

            for (CSVRecord record : records) {
                int subtract = 0;
                boolean sameProduct = sameProductId(articles, record);
                if(sameProduct){
                    subtract = getSubtract(articles, record);
                }
                ArrayList<String> aux = new ArrayList<>();
                for(int i = 0; i < record.size(); i++){
                    if(sameProduct && i == 5){ //Si es la columna
                        int newQuantity = Integer.parseInt(record.get(i)) - subtract;
                        aux.add(String.valueOf(newQuantity));

                    } else{
                        aux.add(record.get(i));
                    }
                }
                csvPrinter.printRecord(aux);
            }
            csvPrinter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int getSubtract(List<RequestedArticleDTO> articles, CSVRecord record) {
        int subtract = 0;
        for(RequestedArticleDTO article : articles){
            if(article.getProductId() == Integer.parseInt(record.get("productId"))){
                subtract = article.getQuantity();
            }
        }
        return subtract;
    }

    private static boolean sameProductId(List<RequestedArticleDTO> articles, CSVRecord record){
        boolean same = false;
        for(RequestedArticleDTO article : articles){
            if(article.getProductId() == Integer.parseInt(record.get("productId"))){
                same = true;
            }
        }
        return same;
    }
}
