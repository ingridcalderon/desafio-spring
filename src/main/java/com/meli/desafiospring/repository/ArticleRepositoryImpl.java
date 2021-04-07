package com.meli.desafiospring.repository;

import com.meli.desafiospring.dto.ArticleDTO;
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
            // Abrir el .csv en buffer de lectura
            File file = ResourceUtils.getFile("classpath:dbProductos.csv");
            readBuffer = new BufferedReader(new FileReader(file));

            // Leer una linea del archivo (Esta línea la descarto porque es la cabecera)
            String line = readBuffer.readLine();
            boolean header = true;
            while (line != null) {
                // step one : converting comma separate String to array of String
                String[] dataLine = line.split(SEPARATOR);
                // step two : convert String array to list of String
                List<String> fixedLenghtList = Arrays.asList(dataLine);
                // step three : copy fixed list to an ArrayList
                ArrayList<String> listOfString = new ArrayList<String>(fixedLenghtList);

                if(!header){
                    ArticleDTO articleAux = new ArticleDTO(listOfString.get(0),listOfString.get(1),listOfString.get(2),listOfString.get(3),listOfString.get(4),listOfString.get(5),listOfString.get(6));
                    articles.add(articleAux);
                }

                header = false;
                // Volver a leer otra línea del fichero
                line = readBuffer.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Cierro el buffer de lectura
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

}
