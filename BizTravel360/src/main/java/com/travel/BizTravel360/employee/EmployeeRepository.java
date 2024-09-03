package com.travel.BizTravel360.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public  interface EmployeeRepository {

    //Crud
    void saveEmployee(Employee employee) throws IOException;
    void deleteEmployeeById(Long employeeId) throws IOException;
    
    void generateAnsSaveRandomEmployee(int count) throws IOException;
}
