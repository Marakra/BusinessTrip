package com.travel.BizTravel360.accommodation.domain;

import com.travel.BizTravel360.accommodation.model.dto.AccommodationDTO;
import com.travel.BizTravel360.accommodation.model.entity.Accommodation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccommodationMapper {
    
    public AccommodationDTO toAccommodation(Accommodation accommodation) {
         AccommodationDTO accommodationDTO = new AccommodationDTO();
         accommodationDTO.setId(accommodation.getId());
         accommodationDTO.setName(accommodation.getNameAccommodation());
         accommodationDTO.setType(accommodation.getTypeAccommodation());
         accommodationDTO.setAddress(accommodation.getAddress());
         accommodationDTO.setCheckIn(accommodation.getCheckIn());
         accommodationDTO.setCheckOut(accommodation.getCheckOut());
         accommodationDTO.setPrice(accommodation.getPrice());
         return accommodationDTO;
    }
    
    public List<AccommodationDTO> toAccommodationList(List<Accommodation> accommodations) {
        return accommodations.stream()
                .map(this::toAccommodation)
                .collect(Collectors.toList());
    }
    
    public Accommodation fromAccommodationDTO(AccommodationDTO accommodationDTO) {
        Accommodation accommodation = new Accommodation();
        accommodation.setId(accommodationDTO.getId());
        accommodation.setNameAccommodation(accommodationDTO.getName());
        accommodation.setTypeAccommodation(accommodationDTO.getType());
        accommodation.setAddress(accommodationDTO.getAddress());
        accommodation.setCheckIn(accommodationDTO.getCheckIn());
        accommodation.setCheckOut(accommodationDTO.getCheckOut());
        accommodation.setPrice(accommodationDTO.getPrice());
        return accommodation;
    }
}
