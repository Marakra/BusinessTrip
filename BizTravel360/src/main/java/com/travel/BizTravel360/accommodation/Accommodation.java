package com.travel.BizTravel360.accommodation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.travel.BizTravel360.accommodation.annotation.ValidDateRangeAccommodation;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@ValidDateRangeAccommodation
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accommodationId;
    
    @NotBlank(message = "Name is a required field!")
    @Size(max = 30, message = "Name must be at most 50 characters")
    private String name;
    
    @NotNull(message = "Type accommodation is a required field!")
    @Enumerated(EnumType.STRING)
    private TypeAccommodation typeAccommodation;
    
    @NotBlank(message = "Address is a required field!")
    @Size(max = 30, message = "Address must be at most 50 characters")
    private String address;
    
    @NotNull(message = "CheckIn is a required field!")
    @Future(message = "CheckIn must be in the future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "Europe/Warsaw")
    private LocalDateTime checkIn;
    
    @NotNull(message = "CheckOut is a required field!")
    @Future(message = "checkOut must be in the future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "Europe/Warsaw")
    private LocalDateTime checkOut;
    
    @NotNull(message = "Price is a required field!")
    @Digits(integer = 5, fraction = 2, message = "Invalid format. Max 5 digits and 2 decimals.")
    private Double price;
    
    public Accommodation() {}
    
    public Accommodation(Long accommodationId, String name, TypeAccommodation typeAccommodation,
                         String address, LocalDateTime checkIn, LocalDateTime checkOut, Double price) {
        this.accommodationId = accommodationId;
        this.name = name;
        this.typeAccommodation = typeAccommodation;
        this.address = address;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.price = price;
    }
}
