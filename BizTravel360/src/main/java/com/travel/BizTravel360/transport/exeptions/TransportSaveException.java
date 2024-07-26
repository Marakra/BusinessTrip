package com.travel.BizTravel360.transport.exeptions;

import java.io.IOException;

public class TransportSaveException extends IOException {
    public TransportSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
