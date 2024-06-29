package com.travel.BizTravel360.file;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;

public interface FileRepository {
    
    public void writerToFile(Object object, String fileName) throws IOException;
    public <T> T readFromFile(TypeReference<T> valueType, String fileName) throws IOException;
}
