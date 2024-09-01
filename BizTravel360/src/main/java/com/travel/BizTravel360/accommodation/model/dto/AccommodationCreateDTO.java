package com.travel.BizTravel360.accommodation.model.dto;

import com.travel.BizTravel360.accommodation.TypeAccommodation;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class AccommodationCreateDTO {
    private Long id;
    private String name;
    private TypeAccommodation type;
    private String address;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Double price;
    @NotNull
    private String slug;
}
