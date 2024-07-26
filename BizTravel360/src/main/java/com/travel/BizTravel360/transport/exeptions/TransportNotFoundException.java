package com.travel.BizTravel360.transport.exeptions;

import java.io.IOException;

public class TransportNotFoundException extends IOException {
    public TransportNotFoundException(Long transportId) {
        super(String.format("No transport with id %s", transportId));
    }
}
