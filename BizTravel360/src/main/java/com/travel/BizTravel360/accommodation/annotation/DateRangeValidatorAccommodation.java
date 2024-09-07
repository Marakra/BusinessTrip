package com.travel.BizTravel360.accommodation.annotation;

import com.travel.BizTravel360.accommodation.model.dto.AccommodationDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateRangeValidatorAccommodation implements ConstraintValidator <ValidDateRangeAccommodation, AccommodationDTO> {
    
    @Override
    public void initialize(ValidDateRangeAccommodation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    
    @Override
    public boolean isValid(AccommodationDTO dto, ConstraintValidatorContext constraint) {
        if (dto == null) {
            return true; // or false if you want to validate non-null objects
        }
        
        boolean isValid = dto.getCheckIn() != null &&
                dto.getCheckOut() != null &&
                dto.getCheckIn().isBefore(dto.getCheckOut());
        
        if (!isValid) {
            constraint.disableDefaultConstraintViolation();
            constraint.buildConstraintViolationWithTemplate("CheckIn must be before arrival date")
                    .addPropertyNode("checkIn")
                    .addConstraintViolation();
        }
        return isValid;
    }
}
