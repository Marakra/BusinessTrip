package com.travel.BizTravel360.transport;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.travel.BizTravel360.delegation.Delegation;
import com.travel.BizTravel360.transport.annotation.ValidDateRangeTransport;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Objects;


@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@ValidDateRangeTransport
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private LocalDateTime departureDateTime;
    
    @NotBlank(message = "Arrival city is a required field!")
    @Size(min = 3, max = 50, message = "Arrival city must be between 3 and 50 characters")
    private String arrival;
    
    @NotNull(message = "Arrival date is a required field!")
    @Future(message = "Arrival date must be in the future")
    private LocalDateTime arrivalDateTime;
    
    @NotNull(message = "Price is a required field!")
    @Positive(message = "Price must be positive")
    private Double price;
    
    @Builder.Default
    private Boolean isAccepted = false;
    
    @ManyToOne
    @JoinColumn(name = "DELEGATION_ID")
    private Delegation delegation;
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
}