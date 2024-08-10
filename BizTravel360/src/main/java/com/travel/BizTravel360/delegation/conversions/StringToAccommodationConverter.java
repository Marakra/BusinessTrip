package com.travel.BizTravel360.delegation.conversions;

import com.travel.BizTravel360.accommodation.Accommodation;
import com.travel.BizTravel360.accommodation.AccommodationService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StringToAccommodationConverter implements Converter<String, Accommodation> {
    
    private final AccommodationService accommodationService;
    
    public StringToAccommodationConverter(AccommodationService accommodationService) {
        this.accommodationService = accommodationService;
    }
    
    @Override
    public Accommodation convert(String source) {
        Long accommodationId = Long.parseLong(source);
        try {
            return accommodationService.findAccommodationById(accommodationId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
