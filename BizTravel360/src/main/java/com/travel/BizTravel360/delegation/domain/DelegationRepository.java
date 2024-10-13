package com.travel.BizTravel360.delegation.domain;

import com.travel.BizTravel360.delegation.model.entity.Delegation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface DelegationRepository extends JpaRepository<Delegation, Long> {


    Page<Delegation> findByKeywordAndType(@Param("keyword") String keyword, Pageable pageable);
}
