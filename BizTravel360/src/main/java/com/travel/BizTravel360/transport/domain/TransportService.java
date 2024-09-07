package com.travel.BizTravel360.transport.domain;


import com.travel.BizTravel360.transport.model.Transport;
import com.travel.BizTravel360.transport.exeptions.TransportSaveException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TransportService  {

    private final TransportRepository transportRepository;
    private final Validator validator;

    public void save(Transport transport) throws DataAccessException {
        try {
            trimTransport(transport);
            validateTransport(transport);

            transportRepository.save(transport);
        }catch (DataAccessException exp) {
                log.error("Failed to save transport {}", transport);
                throw new TransportSaveException(
                        String.format("Failed to save transport: %s, message: %s.", transport, exp.getMessage()));
            }
    }

    public Page<Transport> findAll(Pageable pageable) {
        return transportRepository.findAll(pageable);
    }

    public Page<Transport> searchATransport(String keyword, Pageable pageable) {
        return transportRepository.findByKeyword(keyword, pageable);
    }
    public void updateTransport(Transport updateTransport) {
        transportRepository.findById(updateTransport.getId());
    }

    public void deleteById(Long transportId) {
        transportRepository.deleteById(transportId);
    }


    public Optional<Transport> getById(Long transportId) {
        return transportRepository.findById(transportId);
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
}