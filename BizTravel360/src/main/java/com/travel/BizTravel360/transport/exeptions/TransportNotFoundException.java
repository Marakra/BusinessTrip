package com.travel.BizTravel360.transport.exeptions;


public class TransportNotFoundException extends RuntimeException {
    public TransportNotFoundException(Long transportId) {
        super(String.format("No found transport with property id: %d", transportId));
    }
}
