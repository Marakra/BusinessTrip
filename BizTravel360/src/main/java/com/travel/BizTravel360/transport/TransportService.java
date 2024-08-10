package com.travel.BizTravel360.transport;

import com.fasterxml.jackson.core.type.TypeReference;
import com.travel.BizTravel360.file.FileService;
import com.travel.BizTravel360.transport.exeptions.TransportNotFoundException;
import com.travel.BizTravel360.transport.exeptions.TransportSaveException;
import jakarta.transaction.Transactional;
import jakarta.validation.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
public class TransportService implements TransportRepository{
    
    private final FileService fileService;
    @Value("${transports.file.path}")
    private String transportFilePath;
    
    public TransportService(FileService fileService,
                            @Value("${transports.file.path}") String transportFilePath) {
        this.fileService = fileService;
        this.transportFilePath = transportFilePath;
    }
    
    @Override
    public void saveTransport(Transport transport) throws IOException {
        try {
            trimTransport(transport);
            validateTransport(transport);
            
            transport.setTransportId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
            List<Transport> transportList = fetchTransportList();
            transportList.add(transport);
            
            fileService.writerToFile(transportList, transportFilePath);
            
            logSuccessMessage(transport);
        } catch (IOException e) {
            log.error("Failed to save transport {}", transport, e);
            throw new TransportSaveException(String.format("Failed to save transport %s", transport), e);
        }
    }
    
    @Override
    public List<Transport> fetchTransportList() throws IOException {
        if (Files.exists(Paths.get(transportFilePath))) {
            List<Transport> transportList = fileService.readFromFile(transportFilePath,
                                                new TypeReference<List<Transport>>() {});
            Collections.reverse(transportList);
            return transportList;
        }
        return new ArrayList<>();
    }
    
    @Override
    public void updateTransport(Transport updateTransport, Long transportId) throws IOException {
        Transport existingTransport = findTransportById(transportId);
        List<Transport> transportList = fetchTransportList();
        
        int index = transportList.indexOf(existingTransport);
        updateTransport.setTransportId(transportId);
        transportList.set(index, updateTransport);
            
        fileService.writerToFile(transportList, transportFilePath);
        logSuccessMessage(updateTransport);
    }
    
    @Override
    public void deleteTransportById(Long transportId) throws IOException {
        List<Transport> transportList = fetchTransportList();
        Transport existingTransport = findTransportById(transportId);
        
        transportList.remove(existingTransport);
        fileService.writerToFile(transportList, transportFilePath);
        logSuccessMessage(existingTransport);
    }
    
    @Override
    public Transport findTransportById (Long transportId) throws IOException {
        List<Transport> transportList = fetchTransportList();
        return transportList.stream()
                .filter(t -> Objects.equals(t.getTransportId(), transportId))
                .findFirst()
                .orElseThrow(() -> new TransportNotFoundException(transportId));
    }
    
    private void validateTransport(Transport transport) {
        ValidatorFactory factory = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator((MessageInterpolator) new ParameterMessageInterpolator())
                .buildValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Transport>> constraintViolations = validator.validate(transport);
        
        if (!constraintViolations.isEmpty()){
            constraintViolations.forEach(validation -> log.error(validation.getMessage()));
            throw new IllegalArgumentException("Invalid transport data");
        }
    }
    
    private void logSuccessMessage(Transport transport){
        String successMessage = String.format("Transport %s (%s) successfully changed. Departure: %s, Arrival: %s.",
                transport.getTypeTransport(),
                transport.getTransportIdentifier(),
                transport.getDeparture(),
                transport.getArrival());
        log.info(successMessage);
    }
    
    private void trimTransport(Transport transport) {
        transport.setTransportIdentifier(transport.getTransportIdentifier().trim());
        transport.setDeparture(transport.getDeparture().trim());
        transport.setArrival(transport.getArrival().trim());
    }
    public List<Transport> getFilteredTransports(String query) throws IOException {
        return fetchTransportList().stream()
                .filter(t -> (t.getTypeTransportAsString() != null && t.getTypeTransportAsString().contains(query)
                        || t.getTransportIdentifier().toLowerCase().contains(query.toLowerCase())
                        || t.getDeparture().toLowerCase().contains(query.toLowerCase())
                        || t.getArrival().toLowerCase().contains(query.toLowerCase())))
                .collect(Collectors.toList());
    }
}