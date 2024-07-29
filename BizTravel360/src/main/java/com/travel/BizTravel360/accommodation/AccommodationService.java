package com.travel.BizTravel360.accommodation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.travel.BizTravel360.accommodation.exeptions.AccommodationNotFoundException;
import com.travel.BizTravel360.accommodation.exeptions.AccommodationSaveException;
import com.travel.BizTravel360.file.FileService;
import jakarta.transaction.Transactional;
import jakarta.validation.*;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
@Transactional
@Service
public class AccommodationService implements AccommodationRepository {
    
    private final FileService fileService;
    @Value("${accommodations.file.path}")
    private String accommodationFilePath;
    
    public AccommodationService(FileService fileService,
                                @Value("${accommodations.file.path}") String accommodationFilePath) {
        this.fileService = fileService;
        this.accommodationFilePath = accommodationFilePath;
    }
    
    @Override
    public void saveAccommodation(Accommodation accommodation) throws IOException {
        try {
            trimAccommodation(accommodation);
            validateAccommodation(accommodation);
            
            accommodation.setAccommodationId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
            List<Accommodation> accommodationList = fetchAccommodationList();
            accommodationList.add(accommodation);
            
            fileService.writerToFile(accommodationList, accommodationFilePath);
            logSuccessMessage(accommodation);
        } catch (IOException e) {
            log.error("Failed to save accommodation {}", accommodation, e);
            throw new AccommodationSaveException(String.format("Failed to save accommodation %s.", accommodation), e);
        }
    }
    
    @Override
    public List<Accommodation> fetchAccommodationList() throws IOException {
        if (Files.exists(Paths.get(accommodationFilePath))){
            List<Accommodation> accommodationList = fileService.readFromFile(accommodationFilePath,
                                                        new TypeReference<List<Accommodation>>() {});
            Collections.reverse(accommodationList);
            return accommodationList;
        }
        return new ArrayList<>();
    }
    
    @Override
    public void updateAccommodation(Accommodation updateAccommodation, Long accommodationId) throws IOException {
        Accommodation existingAccommodation = findAccommodationById(accommodationId);
        List<Accommodation> accommodationList = fetchAccommodationList();
        
        int index = accommodationList.indexOf(existingAccommodation);
        updateAccommodation.setAccommodationId(accommodationId);
        accommodationList.set(index, updateAccommodation);
        
        fileService.writerToFile(accommodationList, accommodationFilePath);
        logSuccessMessage(updateAccommodation);
    }
    
    @Override
    public void deleteAccommodationById(Long accommodationId) throws IOException {
        List<Accommodation> accommodationList = fetchAccommodationList();
        Accommodation existingAccommodation = findAccommodationById(accommodationId);
        
        accommodationList.remove(existingAccommodation);
        fileService.writerToFile(accommodationList, accommodationFilePath);
        logSuccessMessage(existingAccommodation);
    }
    
    @Override
    public Accommodation findAccommodationById(Long accommodationId) throws IOException {
        List<Accommodation> accommodationList = fetchAccommodationList();
        return accommodationList.stream()
                .filter(a -> Objects.equals(a.getAccommodationId(), accommodationId))
                .findFirst()
               .orElseThrow(() -> new AccommodationNotFoundException(accommodationId));
    }
    
    private void validateAccommodation(Accommodation accommodation){
        ValidatorFactory factory = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Accommodation>> constraintViolations = validator.validate(accommodation);
        
        if (!constraintViolations.isEmpty()) {
            constraintViolations.forEach(validation -> log.error(validation.getMessage()));
            throw new IllegalArgumentException("Invalid accommodation data");
        }
    }
    
    private void logSuccessMessage(Accommodation accommodation) {
        String successMessage = String.format("Successfully updated accommodation: %s, type: $s, address: %s, ",
                accommodation.getName(),
                accommodation.getTypeAccommodation(),
                accommodation.getAddress());
        log.info(successMessage);
    }
    
    private void trimAccommodation(Accommodation accommodation) {
        accommodation.setName(accommodation.getName().trim());
        accommodation.setAddress(accommodation.getAddress().trim());
    }
}
