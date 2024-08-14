package com.travel.BizTravel360.delegation.annotation;

import com.travel.BizTravel360.accommodation.Accommodation;
import com.travel.BizTravel360.delegation.Delegation;
import com.travel.BizTravel360.transport.Transport;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DateRangeValidatorDelegation implements ConstraintValidator <ValidDateRangeDelegation, Delegation> {
    
    @Value("${delegation.validation.time-buffer}")
    private int timeBufferHours;
    
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
                if (delegation.getDepartureDateTime().isAfter(transport.getDepartureDateTime().minusHours(timeBufferHours)) ||
                        delegation.getArrivalDateTime().isBefore(transport.getArrivalDateTime().plusHours(timeBufferHours))) {
                    constraint.disableDefaultConstraintViolation();
                    constraint.buildConstraintViolationWithTemplate(String.format("Delegation dates must be at least %s hour before/after transport dates", timeBufferHours))
                            .addPropertyNode("departureDateTime")
                            .addConstraintViolation();
                    return false;
                }
            }
        }
        
        for (Accommodation accommodation : delegation.getAccommodations()) {
            if (accommodation.getCheckIn() != null
                    && accommodation.getCheckOut() != null) {
                if (delegation.getDepartureDateTime().isAfter(accommodation.getCheckIn().minusHours(timeBufferHours)) ||
                        delegation.getArrivalDateTime().isBefore(accommodation.getCheckOut().plusHours(timeBufferHours))) {
                    constraint.disableDefaultConstraintViolation();
                    constraint.buildConstraintViolationWithTemplate(String.format("Delegation dates must be at least %s hour before/after accommodation dates", timeBufferHours))
                            .addPropertyNode("departureDateTime")
                            .addConstraintViolation();
                    return false;
                }
            }
        }
        return true;
    }
}
