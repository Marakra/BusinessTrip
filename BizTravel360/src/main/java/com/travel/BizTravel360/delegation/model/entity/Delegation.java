package com.travel.BizTravel360.delegation.model.entity;

import com.travel.BizTravel360.accommodation.model.dto.AccommodationDTO;
import com.travel.BizTravel360.accommodation.model.entity.Accommodation;
import com.travel.BizTravel360.common.model.entity.BaseEntity;
import com.travel.BizTravel360.transport.model.dto.TransportDTO;
import com.travel.BizTravel360.transport.model.entity.Transport;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "delegation")
@NoArgsConstructor
@TableGenerator(name = "id_gen", initialValue = 2, allocationSize = 40,
        table = "id_gen", pkColumnName = "ENTITY", pkColumnValue = "delegation", valueColumnName = "NEXT_ID")
public class Delegation extends BaseEntity {

    @NotBlank(message = "Name delegation is a required field!")
    @Size(min = 3, max = 50, message = "Name delegation must be between 3 and 50 characters")
    @Column(name = "NAME_DELEGATION")
    private String nameDelegation;

    @NotNull(message = "Employee is a required field!")
    @Column(name = "EMPLOYEE_ID")
    private Long employeeId;

    @NotEmpty(message = "Transport list is a required field!")
    @OneToMany(mappedBy = "delegation", cascade = CascadeType.ALL)
    private List<Transport> transports;

    @NotEmpty(message = "Accommodation list is a required field!")
    @OneToMany(mappedBy = "delegation", cascade = CascadeType.ALL)
    private List<Accommodation> accommodations;

    @Column(name = "TOTAL_PRICE")
    private BigDecimal totalPrice;

    @Column(name = "IS_ACCEPTED")
    private Boolean isAccepted = true;

    @NotNull(message = "Departure date is a required field!")
    @FutureOrPresent(message = "Departure date must be in the present or future")
    @Column(name = "DEPARTURE_DATE")
    private LocalDateTime departureDateTime;

    @NotNull(message = "Arrival date is a required field!")
    @Future(message = "Arrival date must be in the future")
    @Column(name = "ARRIVAL_DATE")
    private LocalDateTime arrivalDateTime;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

}
