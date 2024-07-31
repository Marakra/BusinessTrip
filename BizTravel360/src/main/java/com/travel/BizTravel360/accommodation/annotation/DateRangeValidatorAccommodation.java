package com.travel.BizTravel360.accommodation.annotation;

import com.travel.BizTravel360.accommodation.Accommodation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateRangeValidatorAccommodation implements ConstraintValidator <ValidDateRangeAccommodation, Accommodation> {
    
    @Override
    public void initialize(ValidDateRangeAccommodation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    
    @Override
    public boolean isValid(Accommodation accommodation, ConstraintValidatorContext constraint) {
        if (accommodation == null) {
            return true; // or false if you want to validate non-null objects
        }
        
        boolean isValid = accommodation.getCheckIn() != null &&
                accommodation.getCheckOut() != null &&
                accommodation.getCheckIn().isBefore(accommodation.getCheckOut());
        
        if (!isValid) {
            constraint.disableDefaultConstraintViolation();
            constraint.buildConstraintViolationWithTemplate("CheckIn must be before arrival date")
                    .addPropertyNode("checkIn")
                    .addConstraintViolation();
        }
        return isValid;
    }
}
