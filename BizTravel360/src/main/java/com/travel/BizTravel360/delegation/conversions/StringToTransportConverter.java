//package com.travel.BizTravel360.delegation.conversions;
//
//import com.travel.BizTravel360.transport.model.dto.TransportDTO;
//import com.travel.BizTravel360.transport.domain.TransportService;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.stereotype.Component;
//
//@Component
//public class StringToTransportConverter implements Converter<String, TransportDTO> {
//
//    private final TransportService transportService;
//
//    public StringToTransportConverter(TransportService transportService) {
//        this.transportService = transportService;
//    }
//
//    @Override
//    public TransportDTO convert(String source) {
//        Long transportId = Long.parseLong(source);
//        try {
//            return transportService.findTransportById(transportId);
//            //Todo change Exception to IOException in the next task TranaportDTO
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
