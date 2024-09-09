package com.travel.BizTravel360.employee.exeptions;

import java.io.IOException;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(Long employeeId) {
        super(String.format("No found employee with property id: %d", employeeId));
    }
}
