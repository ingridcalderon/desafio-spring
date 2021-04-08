package com.meli.desafiospring.utils;

import com.meli.desafiospring.dto.PurchaseRequestDTO;
import com.meli.desafiospring.dto.RequestedArticleDTO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CvsWriter {
    public static String[] HEADERS = { "productId", "name", "category", "brand", "price", "quantity",	"freeShipping",	"prestige"};

    public static void updateCsvFile(PurchaseRequestDTO purchaseRequest) throws Exception {
        List<RequestedArticleDTO> articles = purchaseRequest.getArticles();

        try {

            File file = ResourceUtils.getFile("classpath:dbProductos.csv");
            String pathDestino = file.getAbsolutePath();
            CSVParser parser = new CSVParser(new FileReader(file), CSVFormat.DEFAULT);

            Reader in = new FileReader(file);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader(HEADERS)
                    .withFirstRecordAsHeader()
                    .parse(in);
            String edited = file.getAbsolutePath();
            file.delete();
            CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(edited), CSVFormat.DEFAULT.withHeader(HEADERS));

            for (CSVRecord record : records) {
                int substract = 0;
                boolean sameProduct = sameProductId(articles, record);
                if(sameProduct){
                    substract = getSubstract(articles, record);
                }
                ArrayList<String> aux = new ArrayList<>();
                for(int i = 0; i < record.size(); i++){
                    if(sameProduct && i == 5){ //Si es la columna
                        int newQuantity = Integer.parseInt(record.get(i)) - substract;
                        aux.add(String.valueOf(newQuantity));

                    } else{
                        aux.add(record.get(i));
                    }
                }
                csvPrinter.printRecord(aux);
            }
            csvPrinter.flush();
            moverArchivo(pathDestino);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void moverArchivo(String pathDest){
        try {
            //String pathOrigen = args[0];
            String pathDestino = pathDest;
            // Define aqui tu directorio destino
            String fichero = pathDestino;
            /*
            File ficheroCopiar = new File(pathOrigen, fichero);
            File ficheroDestino = new File(pathDestino, fichero);
            if (ficheroCopiar.exists()) {
                Files.copy(Paths.get(ficheroCopiar.getAbsolutePath()), Paths.get(ficheroDestino.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
            } else {
                System.out.println("El fichero " + fichero + " no existe en el directorio " + pathOrigen);
            }
*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int getSubstract(List<RequestedArticleDTO> articles, CSVRecord record) {
        int substract = 0;
        for(RequestedArticleDTO article : articles){
            if(article.getProductId() == Integer.parseInt(record.get("productId"))){
                substract = article.getQuantity();
            }
        }
        return substract;
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
