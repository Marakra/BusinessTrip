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
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "accommodation")
@NoArgsConstructor
@ValidDateRangeAccommodation
@TableGenerator(name = "id_gen", initialValue = 2, allocationSize = 40,
        table = "id_gen", pkColumnName = "ENTITY", pkColumnValue = "accommodation", valueColumnName = "NEXT_ID")
public class Accommodation extends BaseEntity {
    
    @NotBlank(message = "Name is a required field!")
    @Size(max = 30, message = "Name must be at most 30 characters")
    @Column(name = "NAME")
    private String nameAccommodation;
    
    @NotNull(message = "Type accommodation is a required field!")
    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private TypeAccommodation typeAccommodation;
    
    @NotBlank(message = "Address is a required field!")
    @Size(max = 30, message = "Address must be at most 30 characters")
    @Column(name = "ADDRESS")
    private String address;
    
    @NotNull(message = "CheckIn is a required field!")
    @Future(message = "CheckIn must be in the future")
    @Column(name = "CHECK_IN")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime checkIn;
    
    @NotNull(message = "CheckOut is a required field!")
    @Future(message = "checkOut must be in the future")
    @Column(name = "CHECK_OUT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime checkOut;
    
    @NotNull(message = "Price is a required field!")
    @Positive(message = "Price must be positive")
    @Column(name = "PRICE")
    private Double price;
    
    @ManyToOne
    @JoinColumn(name = "DELEGATION_ID")
    private Delegation delegation;
    
    @Transient
    private String description;
}
