package com.travel.BizTravel360.delegation.conversions;

import com.travel.BizTravel360.transport.model.Transport;
import com.travel.BizTravel360.transport.domain.TransportService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StringToTransportConverter implements Converter<String, Transport> {
    
    private final TransportService transportService;
    
    public StringToTransportConverter(TransportService transportService) {
        this.transportService = transportService;
    }
    
    @Override
    public Transport convert(String source) {
        Long transportId = Long.parseLong(source);
        try {
            return transportService.findTransportById(transportId);
            //Todo change Exception to IOException in the next task TranaportDTO
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
