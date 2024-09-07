package com.travel.BizTravel360.delegation.annotation;

import com.travel.BizTravel360.accommodation.Accommodation;
import com.travel.BizTravel360.delegation.Delegation;
import com.travel.BizTravel360.delegation.configuration.AppConfig;
import com.travel.BizTravel360.transport.model.Transport;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class DateRangeValidatorDelegation implements ConstraintValidator <ValidDateRangeDelegation, Delegation> {
    
    private final AppConfig appConfig;
    
    public DateRangeValidatorDelegation(AppConfig appConfig) {
        this.appConfig = appConfig;
    }
    
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
        
        // Find the earliest departure and latest arrival dates among transports
        List<Transport> transports = delegation.getTransports();
        if (transports.isEmpty()) {
            return true; // No transports to validate against
        }
        
        Optional<LocalDateTime> earliestTransportDeparture = transports.stream()
                .map(Transport::getDepartureDateTime)
                .min(Comparator.naturalOrder());
        
        Optional<LocalDateTime> latestTransportArrival = transports.stream()
                .map(Transport::getArrivalDateTime)
                .max(Comparator.naturalOrder());
        
        if (earliestTransportDeparture.isEmpty() || latestTransportArrival.isEmpty()) {
            return true; // No valid transport dates to validate against
        }
        
        LocalDateTime minTransportDate = earliestTransportDeparture.get();
        LocalDateTime maxTransportDate = latestTransportArrival.get();
        
        // Validate that delegation starts earlier and ends later than transport, according to the buffer
        LocalDateTime expectedDelegationStart = minTransportDate.minusHours(appConfig.getTimeBufferHours());
        LocalDateTime expectedDelegationEnd = maxTransportDate.plusHours(appConfig.getTimeBufferHours());
        
        if (delegation.getDepartureDateTime().isAfter(expectedDelegationStart)
                || delegation.getArrivalDateTime().isBefore(expectedDelegationEnd)) {
            constraint.disableDefaultConstraintViolation();
            constraint.buildConstraintViolationWithTemplate(String.format(
                            "Delegation must start at least %s hours before the earliest transport and end at least %s hours after the latest transport",
                            appConfig.getTimeBufferHours(),
                            appConfig.getTimeBufferHours()))
                    .addPropertyNode("departureDateTime")
                    .addConstraintViolation();
            return false;
        }
        
        // Validate each accommodation against the transport date range
        for (Accommodation accommodation : delegation.getAccommodations()) {
            if (accommodation.getCheckIn() != null && accommodation.getCheckOut() != null) {
                if (accommodation.getCheckIn().isBefore(minTransportDate)
                        || accommodation.getCheckOut().isAfter(maxTransportDate)) {
                    constraint.disableDefaultConstraintViolation();
                    constraint.buildConstraintViolationWithTemplate("Accommodation dates must be within the range of transport dates")
                            .addPropertyNode("accommodations")
                            .addConstraintViolation();
                    return false;
                }
            }
        }
        
        return true;
    }
}
