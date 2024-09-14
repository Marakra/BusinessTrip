package com.travel.BizTravel360.delegation;

import com.travel.BizTravel360.delegation.annotation.ValidDateRangeDelegation;
import com.travel.BizTravel360.accommodation.model.entity.Accommodation;
import com.travel.BizTravel360.employee.Employee;
import com.travel.BizTravel360.transport.model.dto.TransportDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@ValidDateRangeDelegation
public class Delegation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long delegationId;
    
    @NotBlank(message = "Name delegation is a required field!")
    @Size(min = 3, max = 50, message = "Name delegation must be between 3 and 50 characters")
    private String nameDelegation;
    
    @NotNull(message = "Employee is a required field!")
    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;
    
    @NotEmpty(message = "Transport list is a required field!")
    @OneToMany(mappedBy = "delegation", cascade = CascadeType.ALL)
    private List<TransportDTO> transports;
    
    @NotEmpty(message = "Accommodation list is a required field!")
    @OneToMany(mappedBy = "delegation", cascade = CascadeType.ALL)
    private List<Accommodation> accommodations;
    
    private BigDecimal totalPrice;
    private Boolean isAccepted = true;
    
    @NotNull(message = "Departure date is a required field!")
    @FutureOrPresent(message = "Departure date must be in the present or future")
    private LocalDateTime departureDateTime;
    
    @NotNull(message = "Arrival date is a required field!")
    @Future(message = "Arrival date must be in the future")
    private LocalDateTime arrivalDateTime;
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
    
}
