package org.infoshere.menu;
import org.infoshere.model.Activity;
import org.infoshere.model.Coach;
import org.infoshere.model.DayOfTheWeek;
import org.infoshere.model.TypeActivity;
import org.infoshere.service.ActivityService;
import org.infoshere.service.BMICalculator;
import org.infoshere.service.CoachActivity;
import org.infoshere.service.FileService;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu implements Menu {
    Scanner sc = new Scanner(System.in);

    @Override
    public void printMenu() {
        System.out.println("1. List of available Coach");
        System.out.println("2. Add Coach");
        System.out.println("3. Activity list");
        System.out.println("4. Add activity list");
        System.out.println("5. BMI calculator");
        System.out.println("6. Exit");
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
            case 1 -> printFunction("coachList");
            case 2 -> printFunction("addCoach");
            case 3 -> printFunction("activityList");
            case 4 -> printFunction("addActivityList");
            case 5 -> printFunction("BMI");
            case 6 -> printFunction("exit");
            default -> System.out.println("Invalid option.");
        }
    }

    public void run() throws IOException {
        printMenu();
        int choice;
        do {
            choice = getChoice();
            selectOption(choice);
        } while (choice != 6);
    }

//    public int getChoice() {
//        try {
//            System.out.println("Enter your selection:");
//            return sc.nextInt();
//        } catch (InputMismatchException e) {
//            System.out.println("Invalid input. Please enter a valid integer.");
//            sc.nextLine();
//            return getChoice();
//        }
//    }

    public int getChoice() {
        while(true) {
            try {
                System.out.println("Enter your selection:");
                int choice = sc.nextInt();
                sc.nextLine();
                return choice;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.nextLine();

            }
        }
    }



    public void printFunction(String choice) throws IOException {
        FileService fileService = new FileService();
        BMICalculator bmiCalculator = new BMICalculator();
        if (choice.equals("coachList")) {
            System.out.println("------------------------------------");
            printHeader(choice);
            fileService.readFromFile("coaches");
            System.out.println("------------------------------------");
        } else if (choice.equals("addCoach")) {
            System.out.println("------------------------------------");
            printHeader(choice);
            newCoach();
            System.out.println("------------------------------------");
        } else if (choice.equals("activityList")) {
            System.out.println("------------------------------------");
            printHeader(choice);
            fileService.readFromFile("activities");
            System.out.println("------------------------------------");
        } else if (choice.equals("addActivityList")) {
            System.out.println("------------------------------------");
            printHeader(choice);
            newActivity();
            System.out.println("------------------------------------");
        } else if (choice.equals("BMI")) {
            System.out.println("------------------------------------");
            double weight = getValidationInputDouble(sc, "Enter your weight in kg:");
            double height = getValidationInputDouble(sc, "Enter your height in cm (Example 1,75):");
            double bmi = bmiCalculator.bmiCalculation(weight, height);
            System.out.println("Your BMI is: " + bmi);
            System.out.println("------------------------------------");
        } else if (choice.equals("exit")) {
            exit();

        }
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

    private static void newCoach(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Create new coach");
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter specialization: ");
        String specialization = scanner.nextLine();

        Coach coach = new Coach(firstName, lastName, specialization);
        CoachActivity coachActivity = new CoachActivity();
        coachActivity.writeToFile(coach);
    }

    private void newActivity() {

            System.out.print("Create new activity: ");
            String nameActivity = sc.nextLine();

            System.out.print("Choose day of week: ");
            String dayOfTheWeek = sc.nextLine();

            System.out.print("Choose type of activity: ");
            String typeActivity = sc.nextLine();

            DayOfTheWeek day = null;
            switch (dayOfTheWeek) {
                case "Monday":
                    day = DayOfTheWeek.MONDAY;
                    break;
                case "Tuesday":
                    day = DayOfTheWeek.TUESDAY;
                    break;
                case "Wednesday":
                    day = DayOfTheWeek.WEDNESDAY;
                    break;
                case "Thursday":
                    day = DayOfTheWeek.THURSDAY;
                    break;
                case "Friday":
                    day = DayOfTheWeek.FRIDAY;
                    break;
                case "Saturday":
                    day = DayOfTheWeek.SATURDAY;
                    break;
                case "Sunday":
                    day = DayOfTheWeek.SUNDAY;
                    break;
                default:
                    System.out.println("Invalid day of the week");
                    return;
            }

            TypeActivity type = null;
            switch (typeActivity) {
                case "Endurance":
                    type = TypeActivity.ENDURANCE;
                    break;
                case "Strength":
                    type = TypeActivity.STRENGTH;
                    break;
                case "Balance":
                    type = TypeActivity.BALANCE;
                    break;
                case "Flexibility":
                    type = TypeActivity.FLEXIBILITY;
                    break;
                default:
                    System.out.println("Invalid type of activity");
                    return;
            }

            Activity activity = new Activity(nameActivity, day, type);
            ActivityService activityService = new ActivityService();
            activityService.writeToFile(activity);
    }
}


