package com.travel.BizTravel360.transport.domain;

import com.travel.BizTravel360.transport.model.dto.TransportDTO;
import com.travel.BizTravel360.transport.model.entity.Transport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransportMapper {

    public TransportDTO toTransportDto(Transport transport) {
        TransportDTO transportDTO = new TransportDTO();
        transportDTO.setId(transport.getId());
        transportDTO.setType(transport.getTypeTransport());
        transportDTO.setIdentifier(transport.getTransportIdentifier());
        transportDTO.setDeparture(transport.getDeparture());
        transportDTO.setDepartureDateTime(transport.getDepartureDateTime());
        transportDTO.setArrival(transport.getArrival());
        transportDTO.setArrivalDateTime(transport.getArrivalDateTime());
        transportDTO.setPrice(transport.getPrice());

        return transportDTO;
    }
    public List<Transport> toTransportList(List<TransportDTO> transports) {
        return transports.stream()
                .map(this::fromTransportDTO)
                .collect(Collectors.toList());
    }

    public Transport fromTransportDTO(TransportDTO transportDTO) {
        Transport trasport = new Transport();
        trasport.setId(transportDTO.getId());
        trasport.setTypeTransport(transportDTO.getType());
        trasport.setTransportIdentifier(transportDTO.getIdentifier());
        trasport.setDeparture(transportDTO.getDeparture());
        trasport.setDepartureDateTime(transportDTO.getDepartureDateTime());
        trasport.setArrival(transportDTO.getArrival());
        trasport.setArrivalDateTime(transportDTO.getArrivalDateTime());
        trasport.setPrice(transportDTO.getPrice());
        return trasport;
    }

    public List<TransportDTO> toTransportDtoList(List<Transport> transports) {
        return transports.stream()
                .map(this::toTransportDto)
                .collect(Collectors.toList());
    }

}
