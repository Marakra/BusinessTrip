package com.travel.BizTravel360.delegation.model.dto;

import com.travel.BizTravel360.accommodation.annotation.ValidDateRangeAccommodation;
import com.travel.BizTravel360.accommodation.model.dto.AccommodationDTO;
import com.travel.BizTravel360.accommodation.model.entity.Accommodation;
import com.travel.BizTravel360.transport.model.dto.TransportDTO;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ValidDateRangeAccommodation
public class DelegationDTO {

    private Long id;

    @NotBlank(message = "Name delegation is a required field!")
    @Size(min = 3, max = 50, message = "Name delegation must be between 3 and 50 characters")
    private String nameDelegation;

    @NotNull(message = "Employee is a required field!")
    private Long employeeId;

    @NotEmpty(message = "Transport list is a required field!")
    private List<TransportDTO> transports;

    @NotEmpty(message = "Accommodation list is a required field!")
    private List<AccommodationDTO> accommodationIds;

    @NotNull(message = "Departure date is a required field!")
    @FutureOrPresent(message = "Departure date must be in the present or future")
    private LocalDateTime departureDateTime;

    @NotNull(message = "Arrival date is a required field!")
    @Future(message = "Arrival date must be in the future")

    private LocalDateTime arrivalDateTime;
    private BigDecimal totalPrice;

    private Boolean isAccepted = true;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
