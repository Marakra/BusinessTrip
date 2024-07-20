package com.travel.BizTravel360.transport;

import com.fasterxml.jackson.core.type.TypeReference;
import com.travel.BizTravel360.file.FileService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import jakarta.validation.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    
    @PostConstruct
    public void init() {
        try {
            if (Files.exists(Paths.get(transportFilePath))){
                this.transports = fileService.readFromFile(new TypeReference<List<Transport>>() {}, transportFilePath);
                
                log.info("Initialized transport list with {} transports.", transports.size());
                for (Transport transport : transports) {
                    formatTransportDates(transport);
                    log.info("Transport: {} ", transport);
                }
            }
        } catch (IOException e) {
            log.error("Failed to read transports form JSON file: {}, message: {}", transportFilePath, e.getMessage());
        }
    }
    
    @Override
    public Transport saveTransport(Transport transport) throws IOException {
        try {
            formatTransportDates(transport);
            trimTransport(transport);
            validateTransport(transport);
            
            transport.setTransportId((UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE));
            transports.add(transport);
            fileService.writerToFile(transports, transportFilePath);
            formatTransportDates(transport);
            log.info("Transport {}", transport);
            return transport;
        }catch (IOException e){
            log.error("Failed to save transport {}", transport);
            throw new RuntimeException("Failed to save transport: " + transport);
        }
    }
    
    @Override
    public List<Transport> fetchTransportList() {
        List<Transport> reverseTransports = new ArrayList<>(this.transports);
        Collections.reverse(reverseTransports);
        return new ArrayList<>(reverseTransports);
    }
    
    @Override
    public Transport updateTransport(Transport updateTransport, Long transportId) throws IOException {
        Transport transportToUpdate = findTransportById(transportId);
        trimTransport(updateTransport);
        
        if (transportToUpdate != null) {
            transportToUpdate.setTypeTransport(updateTransport.getTypeTransport());
            transportToUpdate.setTransportIdentifier(updateTransport.getTransportIdentifier());
            transportToUpdate.setDeparture(updateTransport.getDeparture());
            transportToUpdate.setDepartureDateTime(updateTransport.getDepartureDateTime());
            transportToUpdate.setArrival(updateTransport.getArrival());
            transportToUpdate.setArrivalDateTime(updateTransport.getArrivalDateTime());
            transportToUpdate.setCount(updateTransport.getCount());
            return transportToUpdate;
        }else {
            return null;
        }
    }
    
    @Override
    public void deleteTransportById(Long transportId) throws IOException {
        transports.removeIf(t -> Objects.equals(t.getTransportId(), transportId));
        persistTransport();
    }
    
    @Override
    public Transport findTransportById(Long transportId) throws IOException {
        return transports.stream()
                .filter(t -> Objects.equals(t.getTransportId(), transportId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No transport with id " + transportId));
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
    
    private void persistTransport(){
        try {
            fileService.writerToFile(transports, transportFilePath);
        }catch (FileSystemException e){
            log.error("Failed to persist JSON file system issue: {}, message: {}", transportFilePath, e.getMessage());
        } catch (IOException e) {
            log.error("Failed to persist transport JSON file: {}, message: {}", transportFilePath, e.getMessage());
        }
    }
    
    private void trimTransport(Transport transport) {
        transport.setTransportIdentifier(transport.getTransportIdentifier().trim());
        transport.setDeparture(transport.getDeparture().trim());
        transport.setArrival(transport.getArrival().trim());
    }
    
    private void formatTransportDates(Transport transport) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd [HH:mm]");
        try {
            LocalDateTime departureDateTime = LocalDateTime.parse(transport.getDepartureDateTime().format(formatter), formatter);
            transport.setDepartureDateTime(departureDateTime);
            
            LocalDateTime arrivalDateTime = LocalDateTime.parse(transport.getArrivalDateTime().format(formatter), formatter);
            transport.setArrivalDateTime(arrivalDateTime);
            
            if (departureDateTime.isAfter(arrivalDateTime)) {
                throw new IllegalArgumentException("Departure date and time must be before arrival date and time.");
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use 'yyyy-MM-dd [HH:mm]'.", e);
        }
    }
}
