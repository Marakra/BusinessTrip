package com.travel.BizTravel360.delegation.domain;

import com.travel.BizTravel360.delegation.model.dto.DelegationDTO;
import com.travel.BizTravel360.delegation.model.entity.Delegation;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DelegationMapper {

    public Delegation toDelegation(DelegationDTO dto ) {
        Delegation delegation = new Delegation();
        delegation.setId(dto.getId());
        delegation.setNameDelegation(dto.getNameDelegation());
        delegation.setEmployeeId(dto.getEmployeeId());

        delegation.setAccommodations(dto.getAccommodationIds());
        delegation.setTransports(dto.getTransports());

        delegation.setDepartureDateTime(dto.getDepartureDateTime());
        delegation.setArrivalDateTime(dto.getArrivalDateTime());
        delegation.setTotalPrice(dto.getTotalPrice());

        delegation.setIsAccepted(dto.getIsAccepted());
        delegation.setCreatedAt(dto.getCreatedAt());
        delegation.setUpdatedAt(dto.getUpdatedAt());

        return delegation;
    }

    public List<Delegation> toDelegationList(List<Delegation> delegations) {
        return delegations.stream()
                .map(this::toDelegation)
                .collect(Collectors.toList());
    }

    public DelegationDTO fromDelegation(Delegation delegation ) {
        DelegationDTO delegationDTO = new DelegationDTO();
        delegationDTO.setId(delegation.getId());
        delegationDTO.setNameDelegation(delegation.getNameDelegation());
        delegationDTO.setEmployeeId(delegation.getEmployeeId());

        delegationDTO.setTransports(delegation.getTransports());
        delegationDTO.setAccommodationIds(delegation.getAccommodations());

        delegationDTO.setDepartureDateTime(delegation.getDepartureDateTime());
        delegationDTO.setArrivalDateTime(delegation.getArrivalDateTime());
        delegationDTO.setTotalPrice(delegation.getTotalPrice());

        delegationDTO.setIsAccepted(delegation.getIsAccepted());
        delegationDTO.setCreatedAt(delegation.getCreatedAt());
        delegationDTO.setUpdatedAt(delegation.getUpdatedAt());

        return delegationDTO;
    }


}
