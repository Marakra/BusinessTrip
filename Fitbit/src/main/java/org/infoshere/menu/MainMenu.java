package org.infoshere.menu;
import org.infoshere.service.FileService;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu implements Menu {
    Scanner sc = new Scanner(System.in);

    @Override
    public void printMenu() {
        System.out.println("1. List of available Coach");
        System.out.println("2. Activity list");
        System.out.println("3. BMI calculator");
        System.out.println("10. Exit");
    }

    public static void printHeader(String header) {
        System.out.println("------------------------------------");
        System.out.println("List of available " + header);
        System.out.println("------------------------------------");
    }

    public static void exit() {
        System.out.println("------------------------------------");
        System.out.println("Exit");
        System.out.println("------------------------------------");
    }

    @Override
    public void selectOption(int optionNumber) throws IOException {
        switch (optionNumber) {
            case 1 -> printFunction("Coach");
            case 2 -> printFunction("ActivityList");
            case 3 -> printFunction("BMI");
            case 10 -> printFunction("exit");
            default -> System.out.println("Invalid option.");
        }
    }

    public void run() throws IOException {
        printMenu();
        int choice;
        do {
            choice = getChoice();
            selectOption(choice);
        } while (choice != 10);
    }

    public int getChoice() {
        try {
            System.out.println("Enter your selection:");
            return sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid integer.");
            sc.nextLine();
            return getChoice();
        }
    }

    public void printFunction(String choice) throws IOException {
        FileService fileService = new FileService();
        if (choice.equals("Coach")) {
            System.out.println("------------------------------------");
            printHeader(choice);
            fileService.readFromFile("Coach");
            System.out.println("------------------------------------");
        } else if (choice.equals("ActivityList")) {
            System.out.println("------------------------------------");
            printHeader(choice);
            fileService.readFromFile("ActivityList");
            System.out.println("------------------------------------");
        } else if (choice.equals("BMI")) {
            System.out.println("------------------------------------");
            double weight = getValidationInputDouble(sc, "Enter your weight in kg:");
            double height = getValidationInputDouble(sc, "Enter your height in cm:");
            double bmi = calculateBmi(weight, height);
            System.out.println("Your BMI is: " + bmi);
            System.out.println("------------------------------------");
        } else if (choice.equals("exit")) {
            exit();
        }
    }

    private double calculateBmi(double weight, double height) {
        double bmi = weight / Math.pow(height / 100.0, 2);
        bmi = Math.round(bmi * 100.0) / 100.0;
        return bmi;
    }

    double getValidationInputDouble(Scanner sc, String prompt) {
        double value = 0.0;
        boolean validInput = false;

        while (!validInput) {
            System.out.println(prompt);
            if (sc.hasNextDouble()) {
                value = sc.nextDouble();
                validInput = true;
            } else {
                System.out.println("You did not enter a valid number or illegal content. Please try again!");
                sc.next();
            }
        }
        return value;
    }
}


