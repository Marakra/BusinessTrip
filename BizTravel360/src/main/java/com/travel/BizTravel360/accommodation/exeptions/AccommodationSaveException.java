package com.travel.BizTravel360.accommodation.exeptions;

import java.io.IOException;

public class AccommodationSaveException extends IOException {
    public AccommodationSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
