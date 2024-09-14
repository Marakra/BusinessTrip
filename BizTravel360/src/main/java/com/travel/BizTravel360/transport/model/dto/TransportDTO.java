package com.travel.BizTravel360.transport.model.dto;

import com.travel.BizTravel360.transport.TypeTransport;
import com.travel.BizTravel360.transport.annotation.ValidDateRangeTransport;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@ValidDateRangeTransport
public class TransportDTO {
    private Long id;

    @NotNull(message = "Identifier transport is a required field!")
    @Size(max = 20, message = "Number of transport must be at most 20 characters")
    private String Identifier;

    @NotNull(message = "Type transport is a required field!")
    private TypeTransport type;

    @NotNull(message = "Departure transport is a required field!")
    private String departure;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @NotNull(message = "Departure Date Time is a required field!")
    @Future(message = "Departure Date Time must be in the future")
    private LocalDateTime departureDateTime;

    @NotNull(message = "Arrival transport is a required field!")
    private String arrival;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @NotNull(message = "Arrival Date Time Date Time is a required field!")
    @Future(message = "Arrival Date Time Date Time must be in the future")
    private LocalDateTime arrivalDateTime;

    @NotNull(message = "Price is a required field!")
    @Positive(message = "Price must be positive")
    private Double price;

}