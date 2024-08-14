package com.travel.BizTravel360.accommodation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.travel.BizTravel360.accommodation.exeptions.AccommodationNotFoundException;
import com.travel.BizTravel360.accommodation.exeptions.AccommodationSaveException;
import com.travel.BizTravel360.file.FileService;
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
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
public class AccommodationService implements AccommodationRepository {
    
    private final FileService fileService;
    private String accommodationFilePath;
    
    private final Validator validator;
    
    public AccommodationService(@Value("${accommodations.file.path}") String accommodationFilePath,
                                FileService fileService, Validator validator) {
        this.fileService = fileService;
        this.accommodationFilePath = accommodationFilePath;
        this.validator = validator;
    }
    
    @Override
    public void saveAccommodation(Accommodation accommodation) throws IOException {
        try {
            trimAccommodation(accommodation);
            validateAccommodation(accommodation);
            
            accommodation.setAccommodationId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
            List<Accommodation> accommodationList = loadAccommodationFromFile();
            accommodationList.add(accommodation);
            
            fileService.writerToFile(accommodationList, accommodationFilePath);
        } catch (IOException e) {
            log.error("Failed to save accommodation {}", accommodation, e);
            throw new AccommodationSaveException(String.format("Failed to save accommodation %s.", accommodation), e);
        }
    }
    
    @Override
    public Page<Accommodation> fetchAccommodationPage(Pageable pageable) throws IOException {
        List<Accommodation> accommodationList = loadAccommodationFromFile();
        int totalAccommodationSize = accommodationList.size();
        
        return accommodationList.stream()
                .skip((long) pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(), list -> new PageImpl<>(list, pageable, totalAccommodationSize)));
    }
    
    @Override
    public void updateAccommodation(Accommodation updateAccommodation, Long accommodationId) throws IOException {
        Accommodation existingAccommodation = findAccommodationById(accommodationId);
        List<Accommodation> accommodationList = loadAccommodationFromFile();
        
        int index = accommodationList.indexOf(existingAccommodation);
        updateAccommodation.setAccommodationId(accommodationId);
        accommodationList.set(index, updateAccommodation);
        
        fileService.writerToFile(accommodationList, accommodationFilePath);
    }
    
    @Override
    public void deleteAccommodationById(Long accommodationId) throws IOException {
        List<Accommodation> accommodationList = loadAccommodationFromFile();
        Accommodation existingAccommodation = findAccommodationById(accommodationId);
        
        accommodationList.remove(existingAccommodation);
        fileService.writerToFile(accommodationList, accommodationFilePath);
    }
    
    @Override
    public Accommodation findAccommodationById(Long accommodationId) throws IOException {
        List<Accommodation> accommodationList = loadAccommodationFromFile();
        return accommodationList.stream()
                .filter(a -> Objects.equals(a.getAccommodationId(), accommodationId))
                .findFirst()
               .orElseThrow(() -> new AccommodationNotFoundException(accommodationId));
    }
    
    @Override
    public List<Accommodation> loadAccommodationFromFile() throws IOException {
        if (Files.exists(Paths.get(accommodationFilePath))){
            List<Accommodation> accommodationList = fileService.readFromFile(accommodationFilePath,
                    new TypeReference<List<Accommodation>>() {});
            Collections.reverse(accommodationList);
            return accommodationList;
        }
        return new ArrayList<>();
    }
    
    @Override
    public void generateAndSaveRandomAccommodation(int count) throws IOException {
        List<Accommodation> randomAccommodations = DataGeneratorAccommodation.generateRandomAccommodationsList(count);
        List<Accommodation> existingAccommodations = loadAccommodationFromFile();
        existingAccommodations.addAll(randomAccommodations);
        fileService.writerToFile(existingAccommodations, accommodationFilePath);
    }
    
    public Page<Accommodation> searchAccommodation(String query, TypeAccommodation type, Pageable pageable) throws IOException {
        // Load all accommodations from the file
        List<Accommodation> allAccommodations = loadAccommodationFromFile();
        
        // Filter accommodations
        List<Accommodation> filteredAccommodations = allAccommodations.stream()
                .filter(accommodation ->
                        (query == null || query.isEmpty() ||
                                accommodation.getNameAccommodation().toLowerCase().contains(query.toLowerCase()) ||
                                accommodation.getAddress().toLowerCase().contains(query.toLowerCase()) ||
                                String.valueOf(accommodation.getAccommodationId()).contains(query) &&
                                (type == null || accommodation.getTypeAccommodation() == type))
                ).toList();
        
        // Get total filtered results
        int totalFiltered = filteredAccommodations.size();
        
        // Apply pagination
        List<Accommodation> paginatedAccommodations = filteredAccommodations.stream()
                .skip((long) pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());
        
        // Return as a page
        return new PageImpl<>(paginatedAccommodations, pageable, totalFiltered);
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
