package com.travel.BizTravel360.accommodation.domain;

import com.travel.BizTravel360.accommodation.DataGeneratorAccommodation;
import com.travel.BizTravel360.accommodation.TypeAccommodation;
import com.travel.BizTravel360.accommodation.model.entity.Accommodation;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    
    public Accommodation saveAccommodation(Accommodation accommodation) {
        try {
            trimAccommodation(accommodation);
            validateAccommodation(accommodation);
            accommodation.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
            return accommodationRepository.save(accommodation);
        } catch (Exception e) {
            log.error("Failed to save accommodation {}", accommodation, e);
            throw new RuntimeException(String.format("Failed to save accommodation %s.", accommodation), e);        }
    }
    
    public Page<Accommodation> fetchAccommodationPage(Pageable pageable) {
        return accommodationRepository.findAll(pageable);
    }
    
    public Accommodation updateAccommodation(Accommodation updateAccommodation, Long accommodationId) {
        accommodationRepository.existsById(accommodationId);
        updateAccommodation.setId(accommodationId);
        return accommodationRepository.save(updateAccommodation);
    }
    
    public void deleteAccommodationById(Long accommodationId) {
        accommodationRepository.deleteById(accommodationId);
    }
    
    public Optional<Accommodation> findAccommodationById(Long accommodationId) {
        return accommodationRepository.findById(accommodationId);
    }
    
    //TODO create new generator with correct data to DB
//    public List<Accommodation> generateAndSaveRandomAccommodation(int count)  {
//        List<Accommodation> randomAccommodations = DataGeneratorAccommodation.generateRandomAccommodationsList(count);
//        return accommodationRepository.saveAll(randomAccommodations);
//    }
    
    public Page<Accommodation> searchAccommodation(String query, TypeAccommodation type, Pageable pageable) {
        return accommodationRepository.findByNameAccommodationContainingIgnoreCaseAndTypeAccommodation(query, type, pageable);
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
}

