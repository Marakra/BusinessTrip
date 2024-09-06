package com.travel.BizTravel360.accommodation.domain;

import com.travel.BizTravel360.accommodation.model.dto.AccommodationDTO;
import com.travel.BizTravel360.accommodation.model.entity.Accommodation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccommodationMapper {
    
    private final ModelMapper modelMapper;
    
    public AccommodationMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    
    public AccommodationDTO toAccommodation(Accommodation accommodation) {
        return modelMapper.map(accommodation, AccommodationDTO.class);
    }
    
    public List<AccommodationDTO> toAccommodationList(List<Accommodation> accommodations) {
        return accommodations.stream()
                .map(this::toAccommodation)
                .collect(Collectors.toList());
    }
    
    public Accommodation fromAccommodationDTO(AccommodationDTO accommodationDTO) {
        return modelMapper.map(accommodationDTO, Accommodation.class);
    }
}
