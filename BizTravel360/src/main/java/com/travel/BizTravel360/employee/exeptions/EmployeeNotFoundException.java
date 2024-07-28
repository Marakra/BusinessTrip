package com.travel.BizTravel360.employee.exeptions;

import java.io.IOException;

public class EmployeeNotFoundException extends IOException {
    public EmployeeNotFoundException(Long employeeId) {
        super(String.format("No employee with id %s", employeeId));
    }
}
