package org.infoshere;

import org.infoshere.service.FileService;
import org.infoshere.service.MenuService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) throws IOException {
        FileService fileService = new FileService();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Create name file: ");
        String nameFile = scanner.nextLine();
        System.out.println("Enter here your data: ");
        String content = scanner.nextLine();

        fileService.writeToFile(nameFile, content);

        fileService.readFromFile("qwe");
    }
}
