package com.travel.BizTravel360.delegation;

import com.travel.BizTravel360.accommodation.model.entity.Accommodation;
import com.travel.BizTravel360.accommodation.domain.AccommodationService;
import com.travel.BizTravel360.delegation.exeptions.DelegationSaveException;
import com.travel.BizTravel360.employee.EmployeeService;
import com.travel.BizTravel360.transport.model.dto.TransportDTO;
import com.travel.BizTravel360.transport.domain.TransportService;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class DelegationService implements DelegationRepository {
    
    private final EmployeeService employeeService;
    private final TransportService transportService;
    private final AccommodationService accommodationService;
    
    private final Validator validator;
    
    
    @Override
    public void createDelegation(Delegation delegation) throws IOException {
        try {
            trimDelegation(delegation);
            validateDelegation(delegation);
            validateDelegation(delegation);
            
        }catch (Exception e) {
            log.error("Failed to create delegation {}", delegation);
            throw new DelegationSaveException(String.format("Failed to create delegation: %s", delegation), e);
        }
    }
    
    //Todo make this in the task where will be changing Delegation
//    @Override
//    public Page<Delegation> fetchDelegationPage(Pageable pageable) throws IOException {
//
//    }
    
    
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
    
    private BigDecimal calculatorTotalPrice(List<TransportDTO> transports, List<Accommodation> accommodations) {
        BigDecimal transportPrice = transports.stream()
                .map(transport -> BigDecimal.valueOf(transport.getPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal accommodationPrice = accommodations.stream()
                .map(accommodation -> BigDecimal.valueOf(accommodation.getPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        return transportPrice.add(accommodationPrice);
    }
    
    private <T> List<T> fetchEntitiesByIds(List<Long> ids, Function<Long, T> fetchFunction) throws IOException {
        return ids.stream()
                .map(fetchFunction::apply)
                .collect(Collectors.toList());
}
}
