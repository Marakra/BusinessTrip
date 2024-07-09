package com.travel.BizTravel360.transport;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Objects;


@Setter
@Getter

public class Transport {
    private long transportId;
    private TypeTransport typeTransport;
    
    @NotNull(message = "required field!")
    private String departureCity;
    
    @NotNull(message = "required field!")
    private LocalDateTime departureDate;
    
    @NotNull(message = "required field!")
    private LocalDateTime arrivalCity;
    
    @NotNull(message = "required field!")
    private LocalDateTime arrivalDate;
    
    
    @NotNull(message = "required field!")
    @Digits(integer = 5, fraction = 2, message = "Invalid format. Max 5 digits and 2 decimals.")
    private double amount;
    
    public Transport() {}
    
    public Transport(long transportId, TypeTransport typeTransport, String departureCity,
                     LocalDateTime departureDate, LocalDateTime arrivalCity, LocalDateTime arrivalDate, double amount) {
        this.transportId = transportId;
        this.typeTransport = typeTransport;
        this.departureCity = departureCity;
        this.departureDate = departureDate;
        this.arrivalCity = arrivalCity;
        this.arrivalDate = arrivalDate;
        this.amount = amount;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transport transport = (Transport) o;
        return transportId == transport.transportId && Double.compare(amount, transport.amount) == 0
                && typeTransport == transport.typeTransport
                && Objects.equals(departureCity, transport.departureCity)
                && Objects.equals(departureDate, transport.departureDate)
                && Objects.equals(arrivalCity, transport.arrivalCity)
                && Objects.equals(arrivalDate, transport.arrivalDate);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(transportId, typeTransport, departureCity, departureDate, arrivalCity, arrivalDate, amount);
    }
}

