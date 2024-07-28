package com.travel.BizTravel360.accommodation.exeptions;

import java.io.IOException;

public class AccommodationNotFoundException extends IOException {
    public AccommodationNotFoundException(Long accommodationId) {
        super(String.format("Accommodation with id %s not found", accommodationId));
    }
}
