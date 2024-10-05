package com.travel.BizTravel360.delegation.domain;

import com.travel.BizTravel360.accommodation.domain.AccommodationMapper;
import com.travel.BizTravel360.delegation.model.dto.DelegationDTO;
import com.travel.BizTravel360.delegation.model.entity.Delegation;

import com.travel.BizTravel360.employee.domain.EmployeeMapper;
import com.travel.BizTravel360.transport.domain.TransportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class DelegationMapper {

    private final TransportMapper transportMapper;
    private final AccommodationMapper accommodationMapper;


    public Delegation toDelegationDto(DelegationDTO dto) {
        Delegation delegation = new Delegation();
        delegation.setId(dto.getId());
        delegation.setNameDelegation(dto.getNameDelegation());
        delegation.setEmployeeId(dto.getEmployeeId());
        var accommodationList  = accommodationMapper.toAccommodationList(dto.getAccommodations());
        var transportList  = transportMapper.toTransportList(dto.getTransports());

        delegation.setAccommodations(accommodationList);
        delegation.setTransports(transportList);
        delegation.setDepartureDateTime(dto.getDepartureDateTime());
        delegation.setArrivalDateTime(dto.getArrivalDateTime());

        delegation.setTotalPrice(dto.getTotalPrice());

        delegation.setIsAccepted(dto.getIsAccepted());
        delegation.setCreatedAt(dto.getCreatedAt());
        delegation.setUpdatedAt(dto.getUpdatedAt());

        return delegation;
    }

    public List<DelegationDTO> toDelegationList(List<Delegation> delegations) {
        return delegations.stream()
                .map(this::fromDelegationDto)
                .collect(Collectors.toList());
    }

    public DelegationDTO fromDelegationDto(Delegation delegation ) {
        DelegationDTO delegationDTO = new DelegationDTO();
        delegationDTO.setId(delegation.getId());
        delegationDTO.setNameDelegation(delegation.getNameDelegation());
        delegationDTO.setEmployeeId(delegation.getEmployeeId());

        var accommodationListDto  = accommodationMapper.toAccommodationDtoList(delegation.getAccommodations());
        var transportListDto  = transportMapper.toTransportDtoList(delegation.getTransports());

        delegationDTO.setTransports(transportListDto);
        delegationDTO.setAccommodations(accommodationListDto);

        delegationDTO.setDepartureDateTime(delegation.getDepartureDateTime());
        delegationDTO.setArrivalDateTime(delegation.getArrivalDateTime());
        delegationDTO.setTotalPrice(delegation.getTotalPrice());

        delegationDTO.setIsAccepted(delegation.getIsAccepted());
        delegationDTO.setCreatedAt(delegation.getCreatedAt());
        delegationDTO.setUpdatedAt(delegation.getUpdatedAt());

        return delegationDTO;
    }

    public List<Delegation> toDelegationDetList(List<DelegationDTO> delegations) {
        return delegations.stream()
                .map(this::toDelegationDto)
                .collect(Collectors.toList());
    }
}
