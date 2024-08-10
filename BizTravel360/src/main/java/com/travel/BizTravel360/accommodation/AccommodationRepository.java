package com.travel.BizTravel360.accommodation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface AccommodationRepository {
    
    //CRUD
    void saveAccommodation(Accommodation accommodation) throws IOException;
    Page<Accommodation> fetchAccommodationPage(Pageable pageable) throws IOException;
    void updateAccommodation(Accommodation updateAccommodation, Long accommodationId) throws IOException;
    void deleteAccommodationById(Long accommodationId) throws IOException;
    
    Accommodation findAccommodationById(Long accommodationId) throws IOException;
    List<Accommodation> loadAccommodationFromFile() throws IOException;
    void generateAndSaveRandomAccommodation(int count) throws IOException;
}
