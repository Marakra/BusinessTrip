package com.travel.BizTravel360.accommodation.domain;

import com.travel.BizTravel360.accommodation.TypeAccommodation;
import com.travel.BizTravel360.accommodation.exeptions.AccommodationNotFoundException;
import com.travel.BizTravel360.accommodation.exeptions.AccommodationSaveException;
import com.travel.BizTravel360.accommodation.model.dto.AccommodationDTO;
import com.travel.BizTravel360.accommodation.model.entity.Accommodation;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccommodationService{
    
    private final AccommodationRepository accommodationRepository;
    private final Validator validator;
    private final AccommodationMapper mapper;
    
    public void save(AccommodationDTO accommodationDTO) throws DataAccessException {
        try {
            trimAccommodation(accommodationDTO);
            validateAccommodation(accommodationDTO);
            
            Accommodation accommodation = mapper.fromAccommodationDTO(accommodationDTO);
            accommodationRepository.save(accommodation);
        } catch (DataAccessException exp) {
            log.error("Failed to save accommodation {}", accommodationDTO);
            throw new AccommodationSaveException(
                    String.format("Failed to save accommodation: %s, message: %s.", accommodationDTO, exp.getMessage()));
        }
    }

    public Page<AccommodationDTO> findAll(Pageable pageable) {
        Page<Accommodation> accommodationPage = accommodationRepository.findAll(pageable);
        List<AccommodationDTO> accommodationDTOs = mapper.toAccommodationList(accommodationPage.getContent());
        return new PageImpl<>(accommodationDTOs, pageable, accommodationPage.getTotalElements());
    }
    
    public void updateAccommodation(AccommodationDTO updateAccommodationDTO) {
        Accommodation existingAccommodation = accommodationRepository.findById(updateAccommodationDTO.getId())
                .orElseThrow(() -> new AccommodationNotFoundException(updateAccommodationDTO.getId()));
        
        Accommodation updatedAccommodation = mapper.fromAccommodationDTO(updateAccommodationDTO);
        updatedAccommodation.setId(existingAccommodation.getId());
        accommodationRepository.save(updatedAccommodation);
    }
    
    public void deleteById(Long accommodationId) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                        .orElseThrow(() -> new AccommodationNotFoundException(accommodationId));
        accommodationRepository.delete(accommodation);
    }
    
    public Page<AccommodationDTO> searchAccommodation(String keyword, TypeAccommodation type, Pageable pageable) {
        return accommodationRepository.findByKeywordAndType(keyword, type, pageable)
                .map(mapper::toAccommodation);
    }
    
    private void validateAccommodation(AccommodationDTO accommodationDTO){
        Set<ConstraintViolation<AccommodationDTO>> constraintViolations = validator.validate(accommodationDTO);
        if (!constraintViolations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("CheckIn must be before CheckOut date!");
            constraintViolations.forEach(violation -> errorMessage.append("\n").append(violation.getMessage()));
            log.error(errorMessage.toString());
            throw new IllegalArgumentException(errorMessage.toString());
        }
    }
    
    private void trimAccommodation(AccommodationDTO accommodationDTO) {
        accommodationDTO.setName(accommodationDTO.getName().trim());
        accommodationDTO.setAddress(accommodationDTO.getAddress().trim());
    }
    
    public AccommodationDTO getById(Long accommodationId) {
        return accommodationRepository.findById(accommodationId)
                .map(mapper::toAccommodation)
                .orElseThrow(() -> {
                    log.error("Accommodation with ID {} not found", accommodationId);
                    return new AccommodationNotFoundException(accommodationId);
                });
    }
}

