package com.travel.BizTravel360.accommodation.model.dto;

import com.travel.BizTravel360.accommodation.TypeAccommodation;

import java.time.LocalDateTime;


public class AccommodationDTO {
    private Long id;
    private String name;
    private TypeAccommodation type;
    private String address;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Double price;
}
