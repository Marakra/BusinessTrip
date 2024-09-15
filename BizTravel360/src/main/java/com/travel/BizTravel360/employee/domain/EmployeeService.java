package com.travel.BizTravel360.employee.domain;


import com.travel.BizTravel360.employee.DataGeneratorEmployee;
import com.travel.BizTravel360.employee.exeptions.EmployeeNotFoundException;
import com.travel.BizTravel360.employee.exeptions.EmployeeSaveException;
import com.travel.BizTravel360.employee.model.dto.EmployeeDTO;
import com.travel.BizTravel360.employee.model.entity.Employee;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;

import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final Validator validator;
    private final EmployeeMapper mapper;
    
    public void  save(EmployeeDTO employeeDTO) throws DataAccessException {
        try {
            trimEmployee(employeeDTO);
            
            Employee employee = mapper.formEmployeeDTO(employeeDTO);
            validateEmployee(employeeDTO);
            employeeRepository.save(employee);
        } catch (DataAccessException exp) {
            log.error("Failed to save employee {}", employeeDTO);
            throw new EmployeeSaveException(
                    String.format("Failed to save employee %s", employeeDTO, exp.getMessage()));
        }
    }
    
    public Page<EmployeeDTO> findAll(Pageable pageable) {
        Page<Employee> employeePage = employeeRepository.findAll(pageable);
        List<EmployeeDTO> employeeDTOs = mapper.toEmployeeList(employeePage.getContent());
        return new PageImpl<>(employeeDTOs, pageable, employeePage.getTotalElements());
    }
    
    public void updateEmployee(EmployeeDTO updateEmployeeDTO) {
        Employee existingEmployee = employeeRepository.findById(updateEmployeeDTO.getId())
                .orElseThrow(() -> new EmployeeNotFoundException(updateEmployeeDTO.getId()));
        
        Employee updatedEmployee = mapper.formEmployeeDTO(updateEmployeeDTO);
        updatedEmployee.setId(existingEmployee.getId());
        employeeRepository.save(updatedEmployee);
    }
    
    public void deleteById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        employeeRepository.delete(employee);
    }
    
    public Page<EmployeeDTO> searchEmployee(String keyword, Pageable pageable) {
        return employeeRepository.findByKeyword(keyword, pageable)
                .map(mapper::toEmployee);
    }
    
    private void validateEmployee(EmployeeDTO employeeDTO) {
        Set<ConstraintViolation<EmployeeDTO>> constraintViolations = validator.validate(employeeDTO);
        if (!constraintViolations.isEmpty()) {
            constraintViolations.forEach(validation -> log.error(validation.getMessage()));
            throw new IllegalArgumentException("Invalid employee data");
        }
    }

    private void trimEmployee(EmployeeDTO employeeDTO) {
        employeeDTO.setFirstName(employeeDTO.getFirstName().trim());
        employeeDTO.setLastName(employeeDTO.getLastName().trim());
    }
    
    public EmployeeDTO getById(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .map(mapper::toEmployee)
                .orElseThrow(() -> {
                    log.error("Employee with id {} not found", employeeId);
                    return new EmployeeNotFoundException(employeeId);
                });
    }
}