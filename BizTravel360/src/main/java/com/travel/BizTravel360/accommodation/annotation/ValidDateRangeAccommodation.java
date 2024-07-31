package com.travel.BizTravel360.accommodation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DateRangeValidatorAccommodation.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateRangeAccommodation {
    String message() default "CheckIn must be before CheckOut date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
