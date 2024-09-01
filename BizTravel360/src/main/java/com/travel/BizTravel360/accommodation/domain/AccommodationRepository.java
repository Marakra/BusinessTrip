package com.travel.BizTravel360.accommodation.domain;

import com.travel.BizTravel360.accommodation.TypeAccommodation;
import com.travel.BizTravel360.accommodation.model.entity.Accommodation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    Accommodation save(Accommodation accommodation);
    void deleteById(Long accommodationId);
    Page<Accommodation> findByNameAccommodationContainingIgnoreCaseAndTypeAccommodation(String query, TypeAccommodation type, Pageable pageable);
    Page<Accommodation> findAll(Pageable pageable);
    @Override
    Optional<Accommodation> findById(Long id);
}
