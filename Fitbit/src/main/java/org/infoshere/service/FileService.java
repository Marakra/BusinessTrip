package org.infoshere.service;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileService {

    String fileName;


    //todo 2 medtody zapisu i odczytu

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
}
