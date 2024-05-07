package org.infoshere.service;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import static java.lang.String.*;

public class FileService {

    String fileName;

    static final String RESOURCES_PATH = "Fitbit/src/main/java/org/infoshere/resources";



    public void writeToFile(String fileName, String content) {
        String path = Paths.get(RESOURCES_PATH).toAbsolutePath().toString();
        String filePath = path + File.separator + fileName + ".txt";

        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filePath, true));

            writer.write(content + "\n");
            writer.close();
            System.out.println("Dane zostały zapisane do pliku.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFromFile(String fileName) throws IOException {
        String path = Paths.get(RESOURCES_PATH).toAbsolutePath().toString();
        String filePath = path + File.separator + fileName + ".txt";

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line = reader.readLine();
        while (line != null) {
            System.out.println(line);
            line = reader.readLine();
        }
        reader.close();
    }
}
