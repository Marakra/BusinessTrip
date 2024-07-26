package com.travel.BizTravel360.file;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FileService implements FileRepository {
    
    private final ObjectMapper objectMapper;
    
    public FileService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    
    @Override
    public void writerToFile(Object object, String fileName) throws IOException {
        validatePath(fileName);
        
        try {
            objectMapper.writeValue(new File(fileName), object);
            log.info("The operation was completed successfully for this file: {}", fileName);
            Path path = Paths.get(fileName);
            if (object instanceof List) {
                objectMapper.writeValue(path.toFile(), object);
                log.info("The list was successfully written to file: {}", fileName);
            } else {
                List<Object> list = new ArrayList<>();
                list.add(object);
                objectMapper.writeValue(path.toFile(), list);
                log.info("The single object was successfully written to file as a list: {}", fileName);
            }
        } catch (IOException e) {
            log.error("Failed to write object to file {}, due to IOException: {}", fileName, e.getMessage());
            throw e;
        }
    }
    
    
    @Override
    public <T> T readFromFile(String fileName, TypeReference<T> typeReference) throws IOException {
        validatePath(fileName);
        
        Path path = Paths.get(fileName);
        if (Files.notExists(path) || Files.size(path) == 0) {
            return objectMapper.readValue("[]", typeReference);
        }
        
        try {
            return objectMapper.readValue(path.toFile(), typeReference);
        } catch (IOException e) {
            log.error("Failed to read object from file {}, due to IOException: {}", fileName, e.getMessage());
            throw e;
        }
    }
    
    private void validatePath(String fileName) {
        Path path = Paths.get(fileName).getParent();
        if (path != null && !Files.notExists(path)) {
            try {
                Files.createDirectories(path);
                log.info("Created directories for path: {}", path);
            } catch (IOException e) {
                log.error("Failed to create directories for path: {}, due to IOException: {}", path, e.getMessage());
                throw new RuntimeException("Failed to create directories for path: " + path, e);
            }
        }
    }
}
