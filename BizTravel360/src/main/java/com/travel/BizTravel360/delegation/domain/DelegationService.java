package com.travel.BizTravel360.delegation.domain;


import com.travel.BizTravel360.accommodation.exeptions.AccommodationNotFoundException;
import com.travel.BizTravel360.accommodation.model.entity.Accommodation;
import com.travel.BizTravel360.delegation.exeptions.DelegationNotFoundException;
import com.travel.BizTravel360.delegation.model.dto.DelegationDTO;
import com.travel.BizTravel360.delegation.model.entity.Delegation;
import com.travel.BizTravel360.transport.exeptions.TransportSaveException;
import com.travel.BizTravel360.transport.model.entity.Transport;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DelegationService {


    private final DelegationRepository delegationRepository;
    private final Validator validator;
    private final DelegationMapper mapper;


    public void save(DelegationDTO delegationDTO) throws DataAccessException {
        try {
            trimDelegation(delegationDTO);
            Delegation delegation = mapper.toDelegationDto(delegationDTO);

            delegationRepository.save(delegation);
        } catch (DataAccessException exp) {
            log.error("Failed to save transport {}", delegationDTO);
            throw new TransportSaveException(
                    String.format("Failed to save transport: %s, message: %s.", delegationDTO, exp.getMessage()));
        }
    }


    public Page<DelegationDTO> findAll(Pageable pageable) {
        Page<Delegation> delegationPage = delegationRepository.findAll(pageable);
        List<DelegationDTO> delegationPages = mapper.toDelegationList(delegationPage.getContent());
        return new PageImpl<>(delegationPages, pageable, delegationPage.getTotalElements());
    }

    private void validateDelegation(DelegationDTO delegationDTO) {
        Set<ConstraintViolation<DelegationDTO>> constraintViolations = validator.validate(delegationDTO);
        if (!constraintViolations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("CheckIn must be before CheckOut date!");
            constraintViolations.forEach(violation -> errorMessage.append("\n").append(violation.getMessage()));
            log.error(errorMessage.toString());
            throw new IllegalArgumentException(errorMessage.toString());
        }
    }


    public void updateDelegation(DelegationDTO updateDelegationDTO) {
        Delegation existingDelegation = delegationRepository.findById(updateDelegationDTO.getId())
                .orElseThrow(() -> new DelegationNotFoundException(updateDelegationDTO.getId()));

        Delegation updatedDelegation = mapper.toDelegationDto(updateDelegationDTO);
        updateDelegationDTO.setId(existingDelegation.getId());
        delegationRepository.save(updatedDelegation);
    }

    public void deleteById(Long delegationId) {
        Delegation delegation = delegationRepository.findById(delegationId)
                .orElseThrow(() -> new DelegationNotFoundException(delegationId));
        delegationRepository.delete(delegation);
    }

    public Page<DelegationDTO> searchDelegation(String keyword, Pageable pageable) {
        return delegationRepository.findByKeywordAndType(keyword, pageable)
                .map(mapper::fromDelegationDto);
    }

    public DelegationDTO getById(Long delegationId) {
        return delegationRepository.findById(delegationId)
                .map(mapper::fromDelegationDto)
                .orElseThrow(() -> {
                    log.error("Delegation with ID {} not found", delegationId);
                    return new DelegationNotFoundException(delegationId);
                });
    }



    private void trimDelegation(DelegationDTO delegationDTO) {
        if (delegationDTO.getNameDelegation() != null) {
            delegationDTO.setNameDelegation(delegationDTO.getNameDelegation().trim());
        }
    }

    private BigDecimal calculatorTotalPrice(List<Transport> transports, List<Accommodation> accommodations) {
        BigDecimal transportPrice = transports.stream()
                .map(transport -> BigDecimal.valueOf(transport.getPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal accommodationPrice = accommodations.stream()
                .map(accommodation -> BigDecimal.valueOf(accommodation.getPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return transportPrice.add(accommodationPrice);
    }

    private <T> List<T> fetchEntitiesByIds(List<Long> ids, Function<Long, T> fetchFunction) {
        return ids.stream()
                .map(fetchFunction::apply)
                .collect(Collectors.toList());
    }
}

