package com.travel.BizTravel360.transport;

import com.fasterxml.jackson.core.type.TypeReference;
import com.travel.BizTravel360.file.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransportService implements TransportRepository {
    
    private FileService fileService;
    
    @Value("${transport.file.path}")
    private String fileFilePath;
    
    public TransportService(FileService fileService) {
        this.fileService = fileService;
    }
    
    @Override
    public List<Transport> readAllTransports() throws IOException {
        return fileService.readFromFile(new TypeReference<List<Transport>>(){}, fileFilePath);
    }
}
