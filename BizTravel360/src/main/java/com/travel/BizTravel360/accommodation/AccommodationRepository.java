package com.travel.BizTravel360.accommodation;

import java.io.IOException;
import java.util.List;

public interface AccommodationRepository {

    void saveAccommodation(Accommodation accommodation) throws IOException;
    List<Accommodation> fetchAccommodationList() throws IOException;
    void updateAccommodation(Accommodation updateAccommodation, Long accommodationId) throws IOException;
    void deleteAccommodationById(Long accommodationId) throws IOException;
    Accommodation findAccommodationById(Long accommodationId) throws IOException;
}
