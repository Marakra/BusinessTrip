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

    public static void coachs() {
        System.out.println("------------------------------------");
        System.out.println("List of available Coach");
        System.out.println("------------------------------------");
    }

    public static void activityList() {
        System.out.println("------------------------------------");
        System.out.println("List of available Activity");
        System.out.println("------------------------------------");
    }

    public static void exit() {
        System.out.println("------------------------------------");
        System.out.println("Exit");
        System.out.println("------------------------------------");
    }

}
