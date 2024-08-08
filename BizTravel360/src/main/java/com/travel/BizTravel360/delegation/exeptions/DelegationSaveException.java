package com.travel.BizTravel360.delegation.exeptions;

import java.io.IOException;

public class DelegationSaveException extends IOException {
    public DelegationSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
