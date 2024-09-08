package com.travel.BizTravel360.accommodation.exeptions;

public class AccommodationNotFoundException extends RuntimeException {
    public AccommodationNotFoundException(Long accommodationId) {
        super(String.format("No found accommodation with property id: %d", accommodationId));
    }
}
