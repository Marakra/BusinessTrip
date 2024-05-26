package org.infoshere.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.infoshere.model.Activity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ActivityService {
    
    static final Path pathMain = Paths.get("Fitbit/src/main/java/org/infoshere/resources");
    
    public void writeToFile(Activity activity) {
        String path = pathMain.toAbsolutePath().toString();
        String filePath = path + File.separator + "activities.json";
        String json;
        
        try {
            ObjectMapper ow = new ObjectMapper();
            json = ow.writeValueAsString(activity);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(json + "\n");
            System.out.println("The new activity was saved to a file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
