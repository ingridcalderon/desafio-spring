package com.meli.desafiospring.utils;

import com.meli.desafiospring.dto.ArticleDTO;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CvsReader {
    public static final String SEPARATOR = ",";

    public static List<ArticleDTO> loadDataBase() {
        BufferedReader readBuffer = null;
        List<ArticleDTO> articles = new ArrayList<>();

        try {
            File file = ResourceUtils.getFile("classpath:dbProductos.csv");
            readBuffer = new BufferedReader(new FileReader(file));

            String line = readBuffer.readLine();
            boolean header = true;

            while (line != null) {
                String[] dataLine = line.split(SEPARATOR);
                List<String> fixedLengthList = Arrays.asList(dataLine);
                ArrayList<String> listOfString = new ArrayList<>(fixedLengthList);

                if(!header){
                    ArticleDTO articleAux = new ArticleDTO(listOfString.get(0),listOfString.get(1),listOfString.get(2),listOfString.get(3),listOfString.get(4),listOfString.get(5),listOfString.get(6),listOfString.get(7));
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
}
