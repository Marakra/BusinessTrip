package com.travel.BizTravel360.transport.domain;

import com.travel.BizTravel360.transport.model.Transport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TransportRepository extends JpaRepository<Transport, Long> {

    Page<Transport> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

}
