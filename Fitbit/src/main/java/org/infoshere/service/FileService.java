package org.infoshere.service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static java.lang.String.*;

public class FileService {

    static final Path pathMain = Paths.get("Fitbit/src/main/java/org/infoshere/resources");

    public void writeToFile(String fileName, String content) {
        String path = pathMain.toAbsolutePath().toString();
        String filePath = path + File.separator + fileName + ".json";

        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(content + "\n"); //delete close
            System.out.println("Dane zostały zapisane do pliku.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFromFile(String fileName) throws IOException {
        String path = pathMain.toAbsolutePath().toString();
        String filePath = path + File.separator + fileName + ".json";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
