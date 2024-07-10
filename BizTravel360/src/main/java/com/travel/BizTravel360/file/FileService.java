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
import java.util.logging.ErrorManager;

@Slf4j
@Service
public class FileService implements FileRepository {

    private final ObjectMapper objectMapper;
    
    public FileService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    
    
    @Override
    public void writerToFile(Object object, String fileName) throws IOException {
        try {
            Path path = Paths.get(fileName).getParent();
            if (path != null && !Files.exists(path)) {
                Files.createDirectories(path);
            }
            objectMapper.writeValue(new File(fileName), object);
        } catch (FileSystemException e) {
            log.error("Failed to write object to file due to file system issue: {}", fileName, e);
            throw new FileSystemException(fileName, null, "Failed to write object to file due to file system issue");
        }
    }
    
    @Override
    public <T> T readFromFile(TypeReference<T> valueTypeRef, String fileName) throws IOException {
        try {
            return objectMapper.readValue(new File(fileName), valueTypeRef);
        } catch (FileSystemException e) {
            log.error("Failed to write object to file due to file system issue: {}", fileName, e);
            throw new FileSystemException(fileName, null, "Failed to write object to file due to file system issue");
        }
    }
}
