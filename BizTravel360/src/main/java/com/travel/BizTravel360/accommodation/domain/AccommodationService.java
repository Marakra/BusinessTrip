package com.travel.BizTravel360.accommodation.domain;

import com.travel.BizTravel360.accommodation.exeptions.AccommodationNotFoundException;
import com.travel.BizTravel360.accommodation.exeptions.AccommodationSaveException;
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
    
    public void save(Accommodation accommodation) throws DataAccessException {
        try {
            trimAccommodation(accommodation);
            validateAccommodation(accommodation);
            
            accommodationRepository.save(accommodation);
        } catch (DataAccessException exp) {
            log.error("Failed to save accommodation {}", accommodation);
            throw new AccommodationSaveException(
                    String.format("Failed to save accommodation: %s, message: %s.", accommodation, exp.getMessage()));
        }
    }
    
    public Page<Accommodation> findAll(Pageable pageable) {
        return accommodationRepository.findAll(pageable);
    }
    
    public void updateAccommodation(Accommodation updateAccommodation) {
        accommodationRepository.findById(updateAccommodation.getId());
        
    }
    
    public void deleteById(Long accommodationId) {
        accommodationRepository.deleteById(accommodationId);
    }
    
    public Page<Accommodation> searchAccommodation(String keyword, Pageable pageable) {
        return accommodationRepository.findByKeyword(keyword, pageable);
    }
    
    private void validateAccommodation(Accommodation accommodation){
        Set<ConstraintViolation<Accommodation>> constraintViolations = validator.validate(accommodation);
        if (!constraintViolations.isEmpty()) {
            constraintViolations.forEach(validation -> log.error(validation.getMessage()));
            throw new IllegalArgumentException("Invalid accommodation data");
        }
    }
    
    private void trimAccommodation(Accommodation accommodation) {
        accommodation.setNameAccommodation(accommodation.getNameAccommodation().trim());
        accommodation.setAddress(accommodation.getAddress().trim());
    }
    
    public Optional<Accommodation> getById(Long accommodationId) {
        return accommodationRepository.findById(accommodationId);
    }
}

