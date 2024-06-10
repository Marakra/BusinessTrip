package com.travel.BizTravel360.json;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class Json {

    private final ObjectMapper objectMapper;
    
    public Json(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    
    public void writerToFile(Object object, String fileName) throws IOException {
        objectMapper.writeValue(new File(fileName), object);
    }
    
    public <T> T readFromFile(Class<T> valueType, String fileName) throws IOException {
        return objectMapper.readValue(new File(fileName), valueType);
    }
}
