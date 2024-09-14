package com.travel.BizTravel360.transport.domain;

import com.travel.BizTravel360.transport.model.dto.TransportDTO;
import com.travel.BizTravel360.transport.model.entity.Trasport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrasportMapper {

    public TransportDTO toTrasport(Trasport trasport) {
        TransportDTO transportDTO = new TransportDTO();
        transportDTO.setId(trasport.getId());
        transportDTO.setType(trasport.getTypeTransport());
        transportDTO.setIdentifier(trasport.getTransportIdentifier());
        transportDTO.setDeparture(trasport.getDeparture());
        transportDTO.setDepartureDateTime(trasport.getDepartureDateTime());
        transportDTO.setArrival(trasport.getArrival());
        transportDTO.setArrivalDateTime(trasport.getArrivalDateTime());
        transportDTO.setPrice(trasport.getPrice());

        return transportDTO;
    }
    public List<TransportDTO> toTransportList(List<Trasport> transports) {
        return transports.stream()
                .map(this::toTrasport)
                .collect(Collectors.toList());
    }

    public Trasport fromTrasportDTO(TransportDTO transportDTO) {
        Trasport trasport = new Trasport();
        transportDTO.setId(transportDTO.getId());
        transportDTO.setType(transportDTO.getType());
        transportDTO.setIdentifier(transportDTO.getIdentifier());
        transportDTO.setDeparture(transportDTO.getDeparture());
        transportDTO.setDepartureDateTime(transportDTO.getDepartureDateTime());
        transportDTO.setArrival(transportDTO.getArrival());
        transportDTO.setArrivalDateTime(transportDTO.getArrivalDateTime());
        transportDTO.setPrice(transportDTO.getPrice());
        return trasport;
    }

}
