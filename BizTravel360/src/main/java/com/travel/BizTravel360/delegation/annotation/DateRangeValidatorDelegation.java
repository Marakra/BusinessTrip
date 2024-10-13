package com.travel.BizTravel360.delegation.annotation;

import com.travel.BizTravel360.accommodation.model.dto.AccommodationDTO;
import com.travel.BizTravel360.delegation.model.dto.DelegationDTO;
import com.travel.BizTravel360._configuration.AppConfig;
import com.travel.BizTravel360.transport.model.dto.TransportDTO;
import com.travel.BizTravel360.transport.model.entity.Transport;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class DateRangeValidatorDelegation implements ConstraintValidator <ValidDateRangeDelegation, DelegationDTO> {

    private final AppConfig appConfig;

    public DateRangeValidatorDelegation(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    public void initialize(ValidDateRangeDelegation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(DelegationDTO delegationDTO, ConstraintValidatorContext constraint) {
        if (delegationDTO == null) {
            return true;
        }

        boolean isValid = delegationDTO.getDepartureDateTime() != null
                && delegationDTO.getArrivalDateTime() != null
                && delegationDTO.getDepartureDateTime().isBefore(delegationDTO.getArrivalDateTime());

        if (!isValid) {
            constraint.disableDefaultConstraintViolation();
            constraint.buildConstraintViolationWithTemplate("Departure date must be before arrival date")
                    .addPropertyNode("departureDateTime")
                    .addConstraintViolation();
            return false;
        }

        // Find the earliest departure and latest arrival dates among transports
        List<TransportDTO> transports = delegationDTO.getTransports();
        if (transports == null || transports.isEmpty()) {
            return true; // No transports to validate against
        }
        Optional<LocalDateTime> earliestTransportDeparture = transports.stream()
                .map(TransportDTO::getDepartureDateTime)
                .min(Comparator.naturalOrder());

        Optional<LocalDateTime> latestTransportArrival = transports.stream()
                .map(TransportDTO::getArrivalDateTime)
                .max(Comparator.naturalOrder());

        if (earliestTransportDeparture.isEmpty() || latestTransportArrival.isEmpty()) {
            return true; // No valid transport dates to validate against
        }

        LocalDateTime minTransportDate = earliestTransportDeparture.get();
        LocalDateTime maxTransportDate = latestTransportArrival.get();

        // Validate that delegation starts earlier and ends later than transport, according to the buffer
        LocalDateTime expectedDelegationStart = minTransportDate.minusHours(appConfig.getTimeBufferHours());
        LocalDateTime expectedDelegationEnd = maxTransportDate.plusHours(appConfig.getTimeBufferHours());

        if (delegationDTO.getDepartureDateTime().isAfter(expectedDelegationStart)
                || delegationDTO.getArrivalDateTime().isBefore(expectedDelegationEnd)) {
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
        for (AccommodationDTO accommodationDTO : delegationDTO.getAccommodations()) {
            if (accommodationDTO.getCheckIn() != null && accommodationDTO.getCheckOut() != null) {
                if (accommodationDTO.getCheckIn().isBefore(minTransportDate)
                        || accommodationDTO.getCheckOut().isAfter(maxTransportDate)) {
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
