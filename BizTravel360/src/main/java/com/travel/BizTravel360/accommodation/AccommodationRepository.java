package com.travel.BizTravel360.accommodation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface AccommodationRepository {

    void saveAccommodation(Accommodation accommodation) throws IOException;
    Page<Accommodation> fetchAccommodationPage(Pageable pageable) throws IOException;
    void updateAccommodation(Accommodation updateAccommodation, Long accommodationId) throws IOException;
    void deleteAccommodationById(Long accommodationId) throws IOException;
    Accommodation findAccommodationById(Long accommodationId) throws IOException;
}
