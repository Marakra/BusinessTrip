package com.travel.BizTravel360.accommodation.model.dto;

import com.travel.BizTravel360.accommodation.TypeAccommodation;
import com.travel.BizTravel360.accommodation.annotation.ValidDateRangeAccommodation;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@ValidDateRangeAccommodation
public class AccommodationDTO {
    private Long id;
    
    @NotBlank(message = "Name is a required field!")
    @Size(max = 30, message = "Name must be at most 30 characters")
    private String name;
    
    @NotNull(message = "Type accommodation is a required field!")
    private TypeAccommodation type;
    
    @NotBlank(message = "Address is a required field!")
    @Size(max = 30, message = "Address must be at most 30 characters")
    private String address;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @NotNull(message = "CheckIn is a required field!")
    @Future(message = "CheckIn must be in the future")
    private LocalDateTime checkIn;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @NotNull(message = "CheckOut is a required field!")
    @Future(message = "checkOut must be in the future")
    private LocalDateTime checkOut;
    
    @NotNull(message = "Price is a required field!")
    @Positive(message = "Price must be positive")
    private Double price;
}
