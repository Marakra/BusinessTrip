package com.travel.BizTravel360.employee.exeptions;

import java.io.IOException;

public class EmployeeSaveException extends RuntimeException {
    public EmployeeSaveException(String message) {
        super(message);
    }
}
