package org.infoshere.service;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuService {

    public void displayMenu() {
        System.out.println("1. List of available Coach");
        System.out.println("2. Activity list");
        System.out.println("3. Exit");
    }

    public int getChoice() {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Enter your selection:");
            return sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            sc.next();
            return getChoice();
        }
    }

    public static void printHeader(String header) {
        System.out.println("------------------------------------");
        System.out.println("List of available " + header);
        System.out.println("------------------------------------");
    }

    public static void contentList(String choice) throws IOException {
        FileService fileService = new FileService();
        if (choice.equals("Coach")) {
            printHeader(choice);
            fileService.readFromFile("Coach");
            System.out.println("------------------------------------");
        } else if (choice.equals("ActivityList")) {
            printHeader(choice);
            fileService.readFromFile("ActivityList");
            System.out.println("------------------------------------");
        }
    }
    public static void exit() {
        System.out.println("------------------------------------");
        System.out.println("Exit");
        System.out.println("------------------------------------");
    }
}
