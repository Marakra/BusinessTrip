package org.infoshere.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.infoshere.model.Coach;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CoachActivity {
    
    static final Path pathMain = Paths.get("Fitbit/src/main/java/org/infoshere/resources");
    
    public void writeToFile(Coach coach) {
        String path = pathMain.toAbsolutePath().toString();
        String filePath = path + File.separator + "coaches.json";
        String json;
        
        try {
            ObjectMapper ow = new ObjectMapper();
            json = ow.writeValueAsString(coach);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(json + "\n");
            System.out.println("The new coach was saved to a file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
