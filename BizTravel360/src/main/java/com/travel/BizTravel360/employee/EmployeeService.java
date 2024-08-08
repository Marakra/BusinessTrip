package com.travel.BizTravel360.employee;

import com.fasterxml.jackson.core.type.TypeReference;
import com.travel.BizTravel360.employee.exeptions.EmployeeNotFoundException;
import com.travel.BizTravel360.employee.exeptions.EmployeeSaveException;
import com.travel.BizTravel360.file.FileService;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class EmployeeService implements EmployeeRepository {
    
    private final FileService fileService;
    private String employeeFilePath;
    
    private final Validator validator;
    
    public EmployeeService(@Value("${employees.file.path}") String employeeFilePath,
                           FileService fileService,
                           Validator validator) {
        this.fileService = fileService;
        this.employeeFilePath = employeeFilePath;
        this.validator = validator;
    }
    
    @Override
    public void  saveEmployee(Employee employee) throws IOException {
        try {
            trimEmployee(employee);
            validateEmployee(employee);
            
            employee.setEmployeeId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
            List<Employee> employeeList = loadEmployeeFromFile();
            employeeList.add(employee);
            
            fileService.writerToFile(employeeList, employeeFilePath);
        } catch (IOException e) {
            log.error("Failed to save employee {}", employee, e);
            throw new EmployeeSaveException(String.format("Failed to save employee %s", employee), e);
        }
    }
    
    @Override
    public Page<Employee> fetchEmployeePage(Pageable pageable) throws IOException {
        List<Employee> employeeList = loadEmployeeFromFile();
       int totalEmployee = employeeList.size();
        
        return employeeList.stream()
                .skip((long) pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(), list -> new PageImpl<>(list, pageable, totalEmployee)));
    }
    
    @Override
    public void updateEmployee(Employee updateEmployee, Long employeeId) throws IOException {
        Employee existingEmployee = findEmployeeById(employeeId);
        List<Employee> employeeList = loadEmployeeFromFile();
        
        int index = employeeList.indexOf(existingEmployee);
        updateEmployee.setEmployeeId(employeeId);
        employeeList.set(index, updateEmployee);
        
        fileService.writerToFile(employeeList, employeeFilePath);
    }
    
    @Override
    public void deleteEmployeeById(Long employeeId) throws IOException {
        List<Employee> employeeList = loadEmployeeFromFile();
        
        Employee existingEmployee = findEmployeeById(employeeId);
        employeeList.remove(existingEmployee);
        fileService.writerToFile(employeeList, employeeFilePath);
    }
    
    @Override
    public Employee findEmployeeById(Long employeeId) throws IOException {
        List<Employee> employeeList = loadEmployeeFromFile();
        return employeeList.stream()
                .filter(e -> Objects.equals(e.getEmployeeId(), employeeId))
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    }
    
    @Override
    public List<Employee> loadEmployeeFromFile() throws IOException {
        if (Files.exists(Paths.get(employeeFilePath))){
            List<Employee> employeeList = fileService.readFromFile(employeeFilePath,
                    new TypeReference<List<Employee>>() {});
            Collections.reverse(employeeList);
            return employeeList;
        }
        return new ArrayList<>();
    }
    
    @Override
    public void generateAnsSaveRandomEmployee(int count) throws IOException {
        List<Employee> randomEmployee = DataGeneratorEmployee.generateRandomEmployeesList(count);
        List<Employee> existingEmployees = loadEmployeeFromFile();
        existingEmployees.addAll(randomEmployee);
        fileService.writerToFile(existingEmployees, employeeFilePath);
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
