package com.travel.BizTravel360.employee;

import com.fasterxml.jackson.core.type.TypeReference;
import com.travel.BizTravel360.employee.exeptions.EmployeeNotFoundException;
import com.travel.BizTravel360.employee.exeptions.EmployeeSaveException;
import com.travel.BizTravel360.file.FileService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
@Service
public class EmployeeService implements EmployeeRepository {
    
    private final FileService fileService;
    @Value("${employees.file.path}")
    private String employeeFilePath;
    
    private List<Employee> employees = new ArrayList<>();
    
    public EmployeeService(FileService fileService, @Value("${employees.file.path}") String employeeFilePath) {
        this.fileService = fileService;
        this.employeeFilePath = employeeFilePath;
    }
    
    @Override
    public void  saveEmployee(Employee employee) throws IOException {
        try {
            trimEmployee(employee);
            validateEmployee(employee);
            
            employee.setEmployeeId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
            List<Employee> existingEmployees = fetchEmployeeList();
            existingEmployees.add(employee);
            
            fileService.writerToFile(existingEmployees, employeeFilePath);
        } catch (IOException e) {
            log.error("Failed to save employee {}", employee, e);
            throw new EmployeeSaveException(String.format("Failed to save employee %s", employee), e);
        }
    }
    
    @Override
    public List<Employee> fetchEmployeeList() throws IOException {
        if (Files.exists(Paths.get(employeeFilePath))){
            this.employees = fileService.readFromFile(employeeFilePath, new TypeReference<List<Employee>>() {});
            Collections.reverse(employees);
            return employees;
        }
        return new ArrayList<>();
    }
    
    @Override
    public void updateEmployee(Employee updateEmployee, Long employeeId) throws IOException {
        this.employees = fetchEmployeeList();
        
        try {
            Employee existingEmployee = findEmployeeById(employeeId);
            
            int index = employees.indexOf(existingEmployee);
            employees.set(index, updateEmployee);
            
            fileService.writerToFile(employees, employeeFilePath);
        } catch (EmployeeNotFoundException e) {
            log.error("Failed to find employee with id {}", employeeId, e);
        }
    }
    
    @Override
    public void deleteEmployeeById(Long employeeId) throws IOException {
        this.employees = fetchEmployeeList();
        
        try {
            Employee existingEmployee = findEmployeeById(employeeId);
            
            employees.remove(existingEmployee);
            fileService.writerToFile(employees, employeeFilePath);
        } catch (EmployeeNotFoundException e) {
            log.error("Failed to find employee with id {}", employeeId, e);
        }
    }
    
    @Override
    public Employee findEmployeeById(Long employeeId) throws IOException {
        if (employees.isEmpty()){
            this.employees = fileService.readFromFile(employeeFilePath, new TypeReference<List<Employee>>() {});
        }
        return employees.stream()
                .filter(e -> Objects.equals(e.getEmployeeId(), employeeId))
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    }
    
    private void validateEmployee(Employee employee) {
        ValidatorFactory factory = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Employee>> constraintViolations = validator.validate(employee);
        
        if (!constraintViolations.isEmpty()) {
            constraintViolations.forEach(validation -> log.error(validation.getMessage()));
            throw  new IllegalArgumentException("Invalid empoloyee data");
        }
    }
    
    private void trimEmployee(Employee employee) {
        employee.setFirstName(employee.getFirstName().trim());
        employee.setLastName(employee.getLastName().trim());
        employee.setEmail(employee.getEmail().trim());
    }
}
