package com.travel.BizTravel360.accommodation.domain;

import com.travel.BizTravel360.accommodation.TypeAccommodation;
import com.travel.BizTravel360.accommodation.model.entity.Accommodation;
import com.travel.BizTravel360.employee.model.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    //Custom query
    @Query(value = "SELECT * FROM accommodation acc WHERE (acc.name LIKE %:keyword% or acc.address LIKE %:keyword%) and (:type IS NULL OR acc.type = :type)", nativeQuery = true)
    Page<Accommodation> findByKeywordAndType(@Param("keyword") String keyword, @Param("type") TypeAccommodation type, Pageable pageable);
    
    Page<Accommodation> findByEmployee(Employee employee, Pageable pageable);
}
