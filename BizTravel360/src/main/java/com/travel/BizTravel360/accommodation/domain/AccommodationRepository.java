package com.travel.BizTravel360.accommodation.domain;

import com.travel.BizTravel360.accommodation.TypeAccommodation;
import com.travel.BizTravel360.accommodation.model.entity.Accommodation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    Page<Accommodation> findByNameAccommodation(String name, TypeAccommodation type, Pageable pageable);
    
}
