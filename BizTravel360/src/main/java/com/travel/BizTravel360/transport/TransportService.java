package com.travel.BizTravel360.transport;

import com.fasterxml.jackson.core.type.TypeReference;
import com.travel.BizTravel360.file.FileService;
import com.travel.BizTravel360.transport.exeptions.TransportNotFoundException;
import com.travel.BizTravel360.transport.exeptions.TransportSaveException;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import jakarta.validation.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Slf4j
@Transactional
@Service
public class TransportService implements TransportRepository{
    
    private final FileService fileService;
    @Value("${transports.file.path}")
    private String transportFilePath;

    private List<Transport> transports = new ArrayList<>();

    public TransportService(FileService fileService, @Value("${transports.file.path}") String transportFilePath) {
        this.fileService = fileService;
        this.transportFilePath = transportFilePath;
    }
    
    @Override
    public void saveTransport(Transport transport) throws IOException {
        try {
            trimTransport(transport);
            validateTransport(transport);
            
            transport.setTransportId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
            List<Transport> existingTransports = fetchTransportList();
            existingTransports.add(transport);
            
            fileService.writerToFile(existingTransports, transportFilePath);
            
            logSuccessMessage(transport);
        } catch (IOException e) {
            log.error("Failed to save transport {}", transport, e);
            throw new TransportSaveException(String.format("Failed to save transport %s", transport), e);
        }
    }
    
    @Override
    public List<Transport> fetchTransportList() throws IOException {
        if (Files.exists(Paths.get(transportFilePath))) {
            this.transports = fileService.readFromFile(transportFilePath, new TypeReference<List<Transport>>() {});
            Collections.reverse(transports);
            return transports;
        }
        return new ArrayList<>();
    }
    
    @Override
    public Transport updateTransport(Transport updateTransport, Long transportId) throws IOException {
        this.transports = fetchTransportList();
        
        boolean updated = false;
        for (int i = 0; i < transports.size(); i++) {
            if (Objects.equals(transports.get(i).getTransportId(), transportId)) {
                transports.set(i, updateTransport);
                updated = true;
                break;
            }
        }
        if (updated) {
            fileService.writerToFile(transports, transportFilePath);
            log.info("Updated transport with ID: {}", transportId);
            logSuccessMessage(updateTransport);
                return updateTransport;
            } else {
                log.warn("Transport with ID {} not found", transportId);
                return null;
            }
    }
    
    @Override
    public void deleteTransportById(Long transportId) throws IOException {
        List<Transport> transports = fetchTransportList();
        List<Transport> updatedTransports = new ArrayList<>();
        boolean found = false;
        
        for (Transport transport : transports) {
            if (Objects.equals(transport.getTransportId(), transportId)) {
                found = true;
            } else {
                updatedTransports.add(transport);
            }
        }
        if (found) {
            fileService.writerToFile(updatedTransports, transportFilePath);
            log.info("Deleted transport with id {}", transportId);
        } else {
            log.warn("Transport with id {} not found for deletion", transportId);
        }
    }
    @Override
    public Transport findTransportById (Long transportId) throws IOException {
        if (transports.isEmpty()) {
            this.transports = fileService.readFromFile(transportFilePath, new TypeReference<List<Transport>>() {});
        }
        return transports.stream()
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
        String successMessage = String.format("Transport %s (%s) successfully saved. Departure: %s, Arrival: %s.",
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
}
