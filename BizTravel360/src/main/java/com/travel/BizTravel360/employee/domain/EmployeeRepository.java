package com.travel.BizTravel360.employee.domain;

import com.travel.BizTravel360.employee.model.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public  interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    @Query(value = "SELECT * FROM employee emp WHERE (emp.first_name like %:keyword or emp.last_name like %:keyword or emp.position like %:keyword)", nativeQuery = true)
    Page<Employee> findByKeyword(@Param("keyword") String keyWord, Pageable pageable);
    
    Optional<Employee> findByToken(String token);
    
    Optional<Employee> findByEmail(String email);
}
