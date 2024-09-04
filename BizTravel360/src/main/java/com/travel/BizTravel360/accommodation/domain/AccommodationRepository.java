package com.travel.BizTravel360.accommodation.domain;

import com.travel.BizTravel360.accommodation.model.entity.Accommodation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    //Custom query
    @Query(value = "SELECT * FROM accommodation acc WHERE acc.name LIKE %:keyword% OR acc.type LIKE %:keyword%", nativeQuery = true)
    Page<Accommodation> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
