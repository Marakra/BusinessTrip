package org.infoshere.service;
import org.infoshere.model.Activity;
import org.infoshere.model.DayOfTheWeek;
import org.infoshere.model.TypeActivity;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


public class FileService {
    
    static final Path pathMain = Paths.get("Fitbit/src/main/java/org/infoshere/resources");
    
    
    
    
    public void writeNewActive() {
        String path = pathMain.toAbsolutePath().toString();
        String filePath = path + File.separator + "activities.json";
        
        
        
        
        String content= " ";
        Scanner scanner = new Scanner(System.in);
        System.out.print("Add new activities: ");
        String jsonActivity = scanner.nextLine();
        System.out.println();
        
        
        content = jsonActivity;
        
        try (FileWriter writer = new FileWriter(filePath, true)){
            writer.write(content + "\n");
            System.out.println("The new activity was saved to a file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readActivities() throws IOException {
        String path = pathMain.toAbsolutePath().toString();
        String filePath = path + File.separator + "activities.json";
        
        char[] array = new char[100];
        
        try {
            FileReader reader = new FileReader(filePath);
            System.out.println("Activity list: ");
            reader.read(array);
            System.out.println(array);
            reader.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
}
