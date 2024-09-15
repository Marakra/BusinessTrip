package com.travel.BizTravel360.employee.domain;

import com.travel.BizTravel360.employee.model.dto.EmployeeDTO;
import com.travel.BizTravel360.employee.model.entity.Employee;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {
    
    public EmployeeDTO toEmployee(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setFirstName(employee.getFirstName());
        employeeDTO.setLastName(employee.getLastName());
        employeeDTO.setPosition(employee.getPosition());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setPassword(employee.getPassword());
        return employeeDTO;
    }
    
    public List<EmployeeDTO> toEmployeeList(List<Employee> employees) {
        return employees.stream()
                .map(this::toEmployee)
                .collect(Collectors.toList());
    }
    
    public Employee formEmployeeDTO(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setPosition(employeeDTO.getPosition());
        employee.setEmail(employeeDTO.getEmail());
        employee.setPassword(employeeDTO.getPassword());
        return employee;
    }
}
