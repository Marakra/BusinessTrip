package com.travel.BizTravel360.file;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class FileService implements FileRepository {

    private final ObjectMapper objectMapper;
    
    public FileService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    
    
    @Override
    public void writerToFile(Object object, String fileName) throws IOException {
        objectMapper.writeValue(new File(fileName), object);
    }
    
    @Override
    public <T> T readFromFile(TypeReference<T> valueTypeRef, String fileName) throws IOException {
        return objectMapper.readValue(new File(fileName), valueTypeRef);
    }
}
