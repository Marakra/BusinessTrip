package org.infoshere;

import org.infoshere.service.FileService;
import org.infoshere.service.MenuService;

import java.io.BufferedReader;
import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {
        menu();
    }

    private static void menu() throws IOException {
        MenuService menuService = new MenuService();

        int optionNumber;

        do {
            menuService.displayMenu();
            optionNumber = menuService.getChoice();
            switch (optionNumber) {
                case 1:
                    menuService.contentList("Coach");
                    break;
                case 2:
                    menuService.contentList("ActivityList");
                    break;
            }
            if (optionNumber > 3) {
                System.out.println("------------------------------------");
                System.out.println("Off the scale. Please enter a valid number.");
                System.out.println("------------------------------------");
            }
        }
        while (optionNumber != 3);
        menuService.exit();
    }
}

