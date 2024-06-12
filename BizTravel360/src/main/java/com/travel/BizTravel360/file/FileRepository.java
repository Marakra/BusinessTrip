package com.travel.BizTravel360.file;

import java.io.IOException;

public interface FileRepository {
    
    public void writerToFile(Object object, String fileName) throws IOException;
    public <T> T readFromFile(Class<T> valueType, String fileName) throws IOException;
}
