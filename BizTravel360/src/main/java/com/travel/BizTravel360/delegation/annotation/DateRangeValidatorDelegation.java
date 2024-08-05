package com.travel.BizTravel360.delegation.annotation;

import com.travel.BizTravel360.accommodation.Accommodation;
import com.travel.BizTravel360.delegation.Delegation;
import com.travel.BizTravel360.transport.Transport;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class DateRangeValidatorDelegation implements ConstraintValidator <ValidDateRangeDelegation, Delegation> {
    
    @Override
    public void initialize(ValidDateRangeDelegation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    
    @Override
    public boolean isValid(Delegation delegation, ConstraintValidatorContext constraint) {
        if (delegation == null) {
            return true;
        }
        
        boolean isValid = delegation.getDepartureDateTime() != null
                && delegation.getArrivalDateTime() != null
                && delegation.getDepartureDateTime().isBefore(delegation.getArrivalDateTime());
        
        if (!isValid) {
            constraint.disableDefaultConstraintViolation();
            constraint.buildConstraintViolationWithTemplate("Departure date must be before arrival date")
                    .addPropertyNode("departureDateTime")
                    .addConstraintViolation();
            return false;
        }
        
        for (Transport transport : delegation.getTransports()) {
            if (transport.getDepartureDateTime() != null
                    && transport.getArrivalDateTime() != null) {
                if (delegation.getDepartureDateTime().isAfter(transport.getDepartureDateTime().minusHours(1)) ||
                        delegation.getArrivalDateTime().isBefore(transport.getArrivalDateTime().plusHours(1))) {
                    constraint.disableDefaultConstraintViolation();
                    constraint.buildConstraintViolationWithTemplate("Delegation dates must be at least 1 hour before/after transport dates")
                            .addPropertyNode("departureDateTime")
                            .addConstraintViolation();
                    return false;
                }
            }
        }
        
        for (Accommodation accommodation : delegation.getAccommodations()) {
            if (accommodation.getCheckIn() != null
                    && accommodation.getCheckOut() != null) {
                if (delegation.getDepartureDateTime().isAfter(accommodation.getCheckIn().minusHours(1)) ||
                        delegation.getArrivalDateTime().isBefore(accommodation.getCheckOut().plusHours(1))) {
                    constraint.disableDefaultConstraintViolation();
                    constraint.buildConstraintViolationWithTemplate("Delegation dates must be at least 1 hour before/after accommodation dates")
                            .addPropertyNode("departureDateTime")
                            .addConstraintViolation();
                    return false;
                }
            }
        }
        return true;
    }
}
