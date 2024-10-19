package com.travel.BizTravel360.transport.domain;
import com.travel.BizTravel360.accommodation.model.entity.Accommodation;
import com.travel.BizTravel360.employee.model.entity.Employee;
import com.travel.BizTravel360.transport.TypeTransport;

import com.travel.BizTravel360.transport.model.entity.Trasport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TransportRepository extends JpaRepository<Trasport, Long> {

    @Query(value = "SELECT * FROM transport trans WHERE (trans.identifier LIKE %:keyword% or trans.arrival LIKE %:keyword% or trans.departure LIKE %:keyword%) and (:type IS NULL OR trans.type = :type)", nativeQuery = true)
    Page<Trasport> findByKeywordAndType(@Param("keyword") String keyword, @Param("type") TypeTransport type, Pageable pageable);
    
    Page<Trasport> findByEmployee(Employee employee, Pageable pageable);
    
    List<Trasport> findByEmployee(Employee employee);
}
