package com.travel.BizTravel360.transport.domain;

import com.travel.BizTravel360.transport.model.dto.TransportDTO;
import com.travel.BizTravel360.transport.model.entity.Trasport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransportMapper {

    public TransportDTO toTransport(Trasport trasport) {
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
                .map(this::toTransport)
                .collect(Collectors.toList());
    }

    public Trasport fromTransportDTO(TransportDTO transportDTO) {
        Trasport trasport = new Trasport();
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

}
