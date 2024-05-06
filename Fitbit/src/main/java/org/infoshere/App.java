package org.infoshere;

import org.infoshere.service.FileService;
import org.infoshere.service.MenuService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {


    public static void main(String[] args) {
        FileService fileService = new FileService();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Create name file: ");
        String nameFile = scanner.nextLine();
        System.out.println("Enter here your data: ");
        String content = scanner.nextLine();

//        fileService.createFile(
//                nameFile
//        );

        fileService.writeToFile(nameFile, content);
    }









    private static void menu() {
        MenuService menuService = new MenuService();

        int optionNumber;

        do {
            menuService.displayMenu();
            optionNumber = menuService.getChoice();
            switch (optionNumber) {
                case 1:
                    menuService.trainersList();

                    break;
                case 2:
                    menuService.activityList();
                    break;
            }
            if (optionNumber > 3){
                System.out.println("------------------------------------");
                System.out.println("Off the scale. Please enter a valid number.");
                System.out.println("------------------------------------");
            }
        }
        while (optionNumber != 3);
        menuService.exit();
    }


}
