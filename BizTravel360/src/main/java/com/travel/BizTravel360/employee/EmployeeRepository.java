package com.travel.BizTravel360.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public  interface EmployeeRepository {

    void saveEmployee(Employee employee) throws IOException;
    List<Employee> fetchEmployeeList() throws IOException;
    void updateEmployee(Employee updateEmployee, Long employeeId) throws IOException;
    void deleteEmployeeById(Long employeeId) throws IOException;
    Employee findEmployeeById(Long employeeId) throws IOException;

}
