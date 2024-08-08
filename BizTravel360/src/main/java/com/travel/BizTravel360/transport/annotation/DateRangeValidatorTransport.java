package com.travel.BizTravel360.transport.annotation;

import com.travel.BizTravel360.transport.Transport;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateRangeValidatorTransport implements ConstraintValidator <ValidDateRangeTransport, Transport>{
    
    @Override
    public void initialize(ValidDateRangeTransport constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    
    @Override
    public boolean isValid(Transport transport, ConstraintValidatorContext constraint) {
        if (transport == null) {
            return true; // or false if you want to validate non-null objects
        }
        
        boolean isValid = transport.getDepartureDateTime() != null
                && transport.getArrivalDateTime() != null
                && transport.getDepartureDateTime().isBefore(transport.getArrivalDateTime());
        
        if (!isValid) {
            constraint.disableDefaultConstraintViolation();
            constraint.buildConstraintViolationWithTemplate("Departure date must be before arrival date")
                    .addPropertyNode("departureDateTime")
                    .addConstraintViolation();
        }
        return isValid;
    }
}