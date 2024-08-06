package com.travel.BizTravel360.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public  interface EmployeeRepository {
    @Query("select e from Employee e where lower(e.firstName) like lower(concat('%', :query, '%')) or lower(e.lastName) like lower(concat('%', :query, '%')) or lower(e.email) like lower(concat('%', :query, '%'))")
    List<Employee> fullTextSearch(@Param("query") String query);
    void saveEmployee(Employee employee) throws IOException;
    List<Employee> fetchEmployeeList() throws IOException;
    void updateEmployee(Employee updateEmployee, Long employeeId) throws IOException;
    void deleteEmployeeById(Long employeeId) throws IOException;
    Employee findEmployeeById(Long employeeId) throws IOException;

}
