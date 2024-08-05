package com.travel.BizTravel360.delegation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.travel.BizTravel360.accommodation.Accommodation;
import com.travel.BizTravel360.accommodation.AccommodationService;
import com.travel.BizTravel360.delegation.exeptions.DelegationSaveException;
import com.travel.BizTravel360.employee.EmployeeService;
import com.travel.BizTravel360.file.FileService;
import com.travel.BizTravel360.transport.Transport;
import com.travel.BizTravel360.transport.TransportService;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
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
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class DelegationService implements DelegationRepository {
    
    private final FileService fileService;
    private String delegationFilePath;
    
    private final EmployeeService employeeService;
    private final TransportService transportService;
    private final AccommodationService accommodationService;
    
    private final Validator validator;
    
    
    public DelegationService(@Value("${delegations.file.path}") String delegationFilePath,
                             FileService fileService,
                             EmployeeService employeeService,
                             TransportService transportService,
                             AccommodationService accommodationService,
                             Validator validator) {
        this.fileService = fileService;
        this.delegationFilePath = delegationFilePath;
        this.employeeService = employeeService;
        this.transportService = transportService;
        this.accommodationService = accommodationService;
        this.validator = validator;
    }
    
    @Override
    public void createDelegation(Delegation delegation) throws IOException {
        try {
            trimDelegation(delegation);
            validateDelegation(delegation);
            
            //Convert and set associations
            delegation.setEmployee(employeeService.findEmployeeById(delegation.getEmployee().getEmployeeId()));
            
            List<Transport> transports = fetchEntitiesByIds(delegation.getTransports().stream()
                    .map(Transport::getTransportId)
                    .collect(Collectors.toList()), transportId -> {
                try {
                    return transportService.findTransportById(transportId);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            delegation.setTransports(transports);
            
            List<Accommodation> accommodations = fetchEntitiesByIds(delegation.getAccommodations().stream()
                    .map(Accommodation::getAccommodationId)
                    .collect(Collectors.toList()), accommodationId -> {
                try {
                    return accommodationService.findAccommodationById(accommodationId);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            delegation.setAccommodations(accommodations);
            
            Double totalPrice = calculatorTotalPrice(transports, accommodations);
            delegation.setTotalPrice(totalPrice);
            
            validateDelegation(delegation);
            
            delegation.setDelegationId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
            
            List<Delegation> delegationList = loadDelegationFromFile();
            delegationList.add(delegation);
            
            log.info("Writing delegations to file: {}", delegationFilePath);
            fileService.writerToFile(delegationList, delegationFilePath);
            log.info("Delegations written to file successfully.");
        }catch (IOException e) {
            log.error("Failed to create delegation {}", delegation, e);
            throw new DelegationSaveException(String.format("Failed to create delegation: %s", delegation), e);
        }
    }
    
    @Override
    public Page<Delegation> fetchDelegationPage(Pageable pageable) throws IOException {
        List<Delegation> delegationList = loadDelegationFromFile();
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        int totalDelegationSize = delegationList.size();
        
        List<Delegation> pageList;
        
        if (totalDelegationSize <= startItem) {
            pageList = Collections.emptyList();
        } else {
            int toIndex = Math.min(totalDelegationSize + startItem, totalDelegationSize);
            pageList = delegationList.subList(startItem, toIndex);
        }
        return new PageImpl<>(delegationList, pageable, totalDelegationSize);
    }
    
    @Override
    public List<Delegation> loadDelegationFromFile() throws IOException {
        if (Files.exists(Paths.get(delegationFilePath))){
            List<Delegation> delegationList = fileService.readFromFile(delegationFilePath,
                    new TypeReference<List<Delegation>>() {});
            
            Collections.reverse(delegationList);
            log.debug("Fetched and reversed delegation list: {}", delegationList);
            return delegationList;
        }
        
        log.warn("File {} not found", delegationFilePath);
        return new ArrayList<>();
    }
    
    
    @Override
    public Optional<Delegation> fetchDelegationById(Long delegationId) throws IOException {
        List<Delegation> delegationList = loadDelegationFromFile();
        return delegationList.stream()
                .filter(d -> Objects.equals(d.getDelegationId(), delegationId))
                .findFirst();
    }
    
    private void validateDelegation(Delegation delegation) {
        Set<ConstraintViolation<Delegation>> constraintViolations = validator.validate(delegation);
        
        if (!constraintViolations.isEmpty()) {
            constraintViolations.forEach(validation -> log.error(validation.getMessage()));
            throw new IllegalArgumentException("Invalid delegation data");
        }
    }
    
    private void trimDelegation(Delegation delegation) {
        delegation.setNameDelegation(delegation.getNameDelegation().trim());
    }
    
    private Double calculatorTotalPrice(List<Transport> transports, List<Accommodation> accommodations) {
        Double transportPrice = transports.stream()
                .mapToDouble(Transport::getPrice)
                .sum();
        Double accommodationPrice = accommodations.stream()
                .mapToDouble(Accommodation::getPrice)
                .sum();
        return transportPrice + accommodationPrice;
    }
    
    private <T> List<T> fetchEntitiesByIds(List<Long> ids, Function<Long, T> fetchFunction) throws IOException {
        return ids.stream()
                .map(fetchFunction::apply)
                .collect(Collectors.toList());
}
}
