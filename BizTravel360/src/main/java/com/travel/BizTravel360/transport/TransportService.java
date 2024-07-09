package com.travel.BizTravel360.transport;

import com.fasterxml.jackson.core.type.TypeReference;
import com.travel.BizTravel360.file.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class TransportService implements TransportRepository {
    
    private final FileService fileService;
    
    @Value("${transport.file.path}")
    private String transportFilePath;
    
    public TransportService(FileService fileService) {
        this.fileService = fileService;
    }
    
    
    @Override
    public void createTransport(Transport transport) throws Exception {
        List<Transport> transports = readAllTransports();
        long maxId = transports.stream().mapToLong(Transport::getTransportId).max().orElse(0);
        transport.setTransportId(maxId + 1);
        transports.add(transport);
        fileService.writerToFile(transports, transportFilePath);
    }
    
    @Override
    public List<Transport> readAllTransports(){
        try {
            List<Transport> transports = fileService.readFromFile
                    (new TypeReference<List<Transport>>() {
                    }, transportFilePath);
            
            if (transports.isEmpty()) {
                log.warn("List of transport is empty");
            }
            
            Collections.reverse(transports);
            
            for (int i = 0; i < transports.size(); i++) {
                transports.get(i).setTransportId(i + 1);
            }
            return transports;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load transport data", e);
        }
    }
}
