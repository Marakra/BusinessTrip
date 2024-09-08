package com.travel.BizTravel360.employee;


import com.travel.BizTravel360.employee.exeptions.EmployeeSaveException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.io.IOException;

import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeService implements EmployeeRepository {

    
    private final Validator validator;

   

    @Override
    public void  saveEmployee(Employee employee) throws IOException {
        try {
            trimEmployee(employee);
            validateEmployee(employee);
            
        } catch (Exception e) {
            log.error("Failed to save employee {}", employee, e);
            throw new EmployeeSaveException(String.format("Failed to save employee %s", employee), e);
        }
    }
    //Todo change it in the new task
//    @Override
//    public Page<Employee> fetchEmployeePage(Pageable pageable) throws IOException {
//    }
    
    public void updateEmployee(Employee updateEmployee) throws IOException {
    }

    @Override
    public void deleteEmployeeById(Long employeeId) throws IOException {
    
    }
    
    
    @Override
    public void generateAnsSaveRandomEmployee(int count) throws IOException {
        List<Employee> randomEmployee = DataGeneratorEmployee.generateRandomEmployeesList(count);
    }
    
    
    
    private void validateEmployee(Employee employee) {
        Set<ConstraintViolation<Employee>> constraintViolations = validator.validate(employee);

        if (!constraintViolations.isEmpty()) {
            constraintViolations.forEach(validation -> log.error(validation.getMessage()));
            throw new IllegalArgumentException("Invalid employee data");
        }
    }

    private void trimEmployee(Employee employee) {
        employee.setFirstName(employee.getFirstName().trim());
        employee.setLastName(employee.getLastName().trim());
        employee.setEmail(employee.getEmail().trim());
    }
}