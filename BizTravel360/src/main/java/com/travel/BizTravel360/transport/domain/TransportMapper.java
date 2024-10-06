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
        Transport transport = new Transport();
        transport.setId(transportDTO.getId());
        transport.setTypeTransport(transportDTO.getType());
        transport.setTransportIdentifier(transportDTO.getIdentifier());
        transport.setDeparture(transportDTO.getDeparture());
        transport.setDepartureDateTime(transportDTO.getDepartureDateTime());
        transport.setArrival(transportDTO.getArrival());
        transport.setArrivalDateTime(transportDTO.getArrivalDateTime());
        transport.setPrice(transportDTO.getPrice());
        return transport;
    }

    public List<TransportDTO> toTransportDtoList(List<Transport> transports) {
        return transports.stream()
                .map(this::toTransportDto)
                .collect(Collectors.toList());
    }

}
