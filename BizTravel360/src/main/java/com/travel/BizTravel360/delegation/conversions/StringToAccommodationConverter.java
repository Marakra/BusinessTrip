//package com.travel.BizTravel360.delegation.conversions;
//
//import com.travel.BizTravel360.accommodation.domain.AccommodationService;
//import com.travel.BizTravel360.accommodation.model.entity.Accommodation;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.stereotype.Component;
//
//import java.util.Optional;
//
//@Component
//public class StringToAccommodationConverter implements Converter<String, Optional<Accommodation>> {
//
//    private final AccommodationService accommodationService;
//
//    public StringToAccommodationConverter(AccommodationService accommodationService) {
//        this.accommodationService = accommodationService;
//    }
//
//    @Override
//    public Optional<Accommodation> convert(String source) {
//        Long accommodationId = Long.parseLong(source);
//        try {
//            return accommodationService.getById(accommodationId);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
