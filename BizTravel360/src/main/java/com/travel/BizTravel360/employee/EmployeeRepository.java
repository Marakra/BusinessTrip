package com.travel.BizTravel360.employee;

import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public  interface EmployeeRepository {
    
    void saveEmployee(Employee employee) throws IOException;
    List<Employee> fetchEmployeeList() throws IOException;
    Employee updateEmployee(Employee updateEmployee, Long employeeId) throws IOException;
    void deleteEmployeeById(Long employeeId) throws IOException;
    Employee findEmployeeByUuid(Long employeeId) throws IOException;
    
}
