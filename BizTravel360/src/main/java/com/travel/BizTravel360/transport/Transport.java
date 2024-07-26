package com.travel.BizTravel360.transport;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.travel.BizTravel360.transport.annotation.ValidDateRange;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
@Entity
@ValidDateRange
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transportId;
    
    @NotNull(message = "Type of transport is a required field!")
    @Enumerated(EnumType.STRING)
    private TypeTransport typeTransport;
    
    @Size(max = 20, message = "Number of transport must be at most 20 characters")
    private String transportIdentifier;
    
    @NotBlank(message = "Departure city is a required field!")
    @Size(min = 3, max = 50, message = "Departure city must be between 3 and 50 characters")
    private String departure;
    
    @NotNull(message = "Departure date is a required field!")
    @FutureOrPresent(message = "Departure date must be in the present or future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "Europe/Warsaw")
    private LocalDateTime departureDateTime;
    
    @NotBlank(message = "Arrival city is a required field!")
    @Size(min = 3, max = 50, message = "Arrival city must be between 3 and 50 characters")
    private String arrival;
    
    @NotNull(message = "Arrival date is a required field!")
    @Future(message = "Arrival date must be in the future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "Europe/Warsaw")
    private LocalDateTime arrivalDateTime;
    
    @NotNull(message = "Price is a required field!")
    @Digits(integer = 5, fraction = 2, message = "Invalid format. Max 5 digits and 2 decimals.")
    private Double price;
    
    public Transport() {}
    
    public Transport(Long transportId, TypeTransport typeTransport, String transportIdentifier, String departure,
                     LocalDateTime departureDateTime, String arrival, LocalDateTime arrivalDateTime, Double price) {
        this.transportId = transportId;
        this.typeTransport = typeTransport;
        this.transportIdentifier = transportIdentifier;
        this.departure = departure;
        this.departureDateTime = departureDateTime;
        this.arrival = arrival;
        this.arrivalDateTime = arrivalDateTime;
        this.price = price;
    }
}
