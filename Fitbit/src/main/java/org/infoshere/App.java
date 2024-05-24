package org.infoshere;

import org.infoshere.model.Activity;
import org.infoshere.model.DayOfTheWeek;
import org.infoshere.model.TypeActivity;
import org.infoshere.service.ActivityService;
import org.infoshere.service.BMICalculator;
import org.infoshere.service.FileService;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Create new activity: ");
            String nameActivity = scanner.nextLine();
            
            System.out.print("Choose day of week: ");
            String dayOfTheWeek = scanner.nextLine();
            
            System.out.print("Choose type of activity: ");
            String typeActivity = scanner.nextLine();
            
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
            
            activity.toString();
        } finally {
            scanner.close();
        }
        
        
    }
}
