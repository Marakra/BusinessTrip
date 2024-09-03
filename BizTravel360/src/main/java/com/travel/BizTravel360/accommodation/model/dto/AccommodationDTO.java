package com.travel.BizTravel360.accommodation.model.dto;

import com.travel.BizTravel360.accommodation.TypeAccommodation;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccommodationDTO {
    private Long id;
    private String name;
    private TypeAccommodation type;
    private String address;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Double price;
}
