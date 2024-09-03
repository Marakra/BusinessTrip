package com.travel.BizTravel360.transport;

import com.fasterxml.jackson.core.type.TypeReference;
import com.travel.BizTravel360.file.FileService;
import com.travel.BizTravel360.transport.exeptions.TransportNotFoundException;
import com.travel.BizTravel360.transport.exeptions.TransportSaveException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class TransportService implements TransportRepository{
    
    private  final Validator validator;

   

    @Override
    public void saveTransport(Transport transport) throws IOException {
        try {
            trimTransport(transport);
            validateTransport(transport);
            
        } catch (Exception e) {
            log.error("Failed to save transport {}", transport, e);
            throw new TransportSaveException(String.format("Failed to save transport %s", transport), e);
        }
    }
    


    @Override
    public void updateTransport(Transport updateTransport, Long transportId) throws IOException {
    }

    @Override
    public void deleteTransportById(Long transportId) throws IOException {
    }
    
    

    @Override
    public void generateAndSaveRandomTransport(int count) throws IOException {
        List<Transport> randomTransports = DataGeneratorTransport.generateRandomTransportList(count);
    }
    

    private void validateTransport(Transport transport) {
        Set<ConstraintViolation<Transport>> constraintViolations = validator.validate(transport);

        if (!constraintViolations.isEmpty()){
            constraintViolations.forEach(validation -> log.error(validation.getMessage()));
            throw new IllegalArgumentException("Invalid transport data");
        }
    }

    private void trimTransport(Transport transport) {
        transport.setTransportIdentifier(transport.getTransportIdentifier().trim());
        transport.setDeparture(transport.getDeparture().trim());
        transport.setArrival(transport.getArrival().trim());
    }
    
    public Transport findTransportById(Long transportId) {
        return null;
    }
}