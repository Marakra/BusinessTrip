package com.travel.BizTravel360.accommodation.exeptions;

import java.io.IOException;

public class AccommodationSaveException extends RuntimeException {
    public AccommodationSaveException(String message) {
        super(message);
    }
}
