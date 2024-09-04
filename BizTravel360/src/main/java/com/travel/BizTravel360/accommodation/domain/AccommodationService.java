package com.travel.BizTravel360.accommodation.domain;

import com.travel.BizTravel360.accommodation.TypeAccommodation;
import com.travel.BizTravel360.accommodation.exeptions.AccommodationSaveException;
import com.travel.BizTravel360.accommodation.model.entity.Accommodation;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccommodationService{
    
    private final AccommodationRepository accommodationRepository;
    private final Validator validator;
    
    public Accommodation save(Accommodation accommodation) throws DataAccessException {
        try {
            trimAccommodation(accommodation);
            validateAccommodation(accommodation);
            return accommodationRepository.save(accommodation);
        } catch (DataAccessException exp) {
            log.error("Failed to save accommodation {}", accommodation, exp);
            throw new AccommodationSaveException(
                    String.format("Failed to save accommodation: %s, message: %s.", accommodation, exp.getMessage()));
        }
    }
    
    public Page<Accommodation> findAll(Pageable pageable) {
        return accommodationRepository.findAll(pageable);
    }
    
    public void updateAccommodation(Accommodation updateAccommodation) {
        Accommodation accommodation = accommodationRepository.findById(updateAccommodation.getId())
                .orElseThrow(() -> new NoSuchElementException("Accommodation not found"));
        
        accommodation.setNameAccommodation(updateAccommodation.getNameAccommodation());
        accommodation.setTypeAccommodation(updateAccommodation.getTypeAccommodation());
        accommodation.setAddress(updateAccommodation.getAddress());
        accommodation.setCheckIn(updateAccommodation.getCheckIn());
        accommodation.setCheckOut(updateAccommodation.getCheckOut());
        accommodation.setPrice(updateAccommodation.getPrice());
        
        accommodationRepository.save(accommodation);
    }
    
    public void deleteById(Long accommodationId) {
        accommodationRepository.deleteById(accommodationId);
    }
    
    //TODO create new generator with correct data to DB in the new task
//    public List<Accommodation> generateAndSaveRandomAccommodation(int count)  {
//        List<Accommodation> randomAccommodations = DataGeneratorAccommodation.generateRandomAccommodationsList(count);
//        return accommodationRepository.saveAll(randomAccommodations);
//    }
    
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

