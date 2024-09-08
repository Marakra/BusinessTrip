package com.travel.BizTravel360.accommodation.model.entity;

import com.travel.BizTravel360.accommodation.TypeAccommodation;
import com.travel.BizTravel360.accommodation.annotation.ValidDateRangeAccommodation;
import com.travel.BizTravel360.common.model.entity.BaseEntity;
import com.travel.BizTravel360.delegation.Delegation;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "accommodation")
@NoArgsConstructor
@TableGenerator(name = "id_gen", initialValue = 2, allocationSize = 40,
        table = "id_gen", pkColumnName = "ENTITY", pkColumnValue = "accommodation", valueColumnName = "NEXT_ID")
public class Accommodation extends BaseEntity {
    
    @Column(name = "NAME")
    private String nameAccommodation;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private TypeAccommodation typeAccommodation;
    
    @Column(name = "ADDRESS")
    private String address;
    
    @Column(name = "CHECK_IN")
    private LocalDateTime checkIn;
    
    @Column(name = "CHECK_OUT")
    private LocalDateTime checkOut;
    
    @Column(name = "PRICE")
    private Double price;
    
    @ManyToOne
    @JoinColumn(name = "DELEGATION_ID")
    private Delegation delegation;
}
