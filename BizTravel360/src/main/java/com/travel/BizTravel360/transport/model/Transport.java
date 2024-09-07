package com.travel.BizTravel360.transport.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.travel.BizTravel360.delegation.Delegation;
import com.travel.BizTravel360.transport.TypeTransport;

import com.travel.BizTravel360.transport.annotation.ValidDateRangeTransport;
import jakarta.persistence.*;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

//@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "transport")
@NoArgsConstructor
@ValidDateRangeTransport
@TableGenerator(name = "id_gen", initialValue = 2, allocationSize = 40,
        table = "id_gen", pkColumnName = "ENTITY", pkColumnValue = "transport", valueColumnName = "NEXT_ID")
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "id_gen")
    private Long id;

    @NotNull(message = "Type accommodation is a required field!")
    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private TypeTransport typeTransport;


    @NotNull(message = "Identifier accommodation is a required field!")
    @Enumerated(EnumType.STRING)
    @Column(name = "IDENTIFIER")
    @Size(max = 20, message = "Number of transport must be at most 20 characters")
    private String transportIdentifier;


    @NotNull(message = "Departure transport is a required field!")
    @Enumerated(EnumType.STRING)
    @Column(name = "DEPARTURE")
    private String departure;


    @NotNull(message = "Departure Date Time is a required field!")
    @Future(message = "Departure Date Time must be in the future")
    @Column(name = "DEPARTURE_DATE_TIME")
    private LocalDateTime departureDateTime;

    @NotNull(message = "Arrival accommodation is a required field!")
    @Enumerated(EnumType.STRING)
    @Column(name = "ARRIVAL")
    private String arrival;


    @NotNull(message = "Arrival Date Time Date Time is a required field!")
    @Future(message = "Arrival Date Time Date Time must be in the future")
    @Column(name = "ARRIVAL_DATE_TIME")
    private LocalDateTime arrivalDateTime;

    @NotNull(message = "Price is a required field!")
    @Positive(message = "Price must be positive")
    @Column(name = "PRICE")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "DELEGATION_ID")
    private Delegation delegation;

}