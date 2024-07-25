package com.travel.BizTravel360.employee.exeptions;

import java.io.IOException;

public class EmployeeSaveException extends IOException {
    public EmployeeSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
