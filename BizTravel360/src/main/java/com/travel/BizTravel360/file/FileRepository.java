package com.travel.BizTravel360.file;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;

public interface FileRepository {
    
     void writerToFile(Object object, String fileName) throws IOException;
     <T> T readFromFile(String fileName, TypeReference<T> typeReference) throws IOException;
}
