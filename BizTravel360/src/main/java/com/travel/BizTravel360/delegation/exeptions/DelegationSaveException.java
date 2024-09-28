package com.travel.BizTravel360.delegation.exeptions;

import java.io.IOException;

public class DelegationSaveException extends RuntimeException {
    public DelegationSaveException(String message) {
        super(message);
    }
}
