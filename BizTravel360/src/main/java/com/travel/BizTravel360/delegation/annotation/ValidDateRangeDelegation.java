//package com.travel.BizTravel360.delegation.annotation;
//
//import jakarta.validation.Constraint;
//import jakarta.validation.Payload;
//
//import java.lang.annotation.ElementType;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
//
//@Constraint(validatedBy = DateRangeValidatorDelegation.class)
//@Target({ElementType.TYPE})
//@Retention(RetentionPolicy.RUNTIME)
//public @interface ValidDateRangeDelegation {
//    String message() default "Invalid Date Range For Delegation";
//    Class<?>[] groups() default {};
//    Class<? extends Payload>[] payload() default {};
//}
