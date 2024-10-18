package com.travel.BizTravel360.transport.model.entity;

import com.travel.BizTravel360.common.model.entity.BaseEntity;
import com.travel.BizTravel360.employee.model.entity.Employee;
import com.travel.BizTravel360.transport.TypeTransport;
import com.travel.BizTravel360.transport.annotation.ValidDateRangeTransport;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "transport")
@NoArgsConstructor
@TableGenerator(name = "id_gen", initialValue = 2, allocationSize = 40,
        table = "id_gen", pkColumnName = "ENTITY", pkColumnValue = "transport", valueColumnName = "NEXT_ID")
public class Trasport extends BaseEntity {

    @Column(name = "TYPE")
    private TypeTransport typeTransport;


    @Column(name = "IDENTIFIER")
    private String transportIdentifier;

    @Column(name = "DEPARTURE")
    private String departure;

    @Column(name = "DEPARTURE_DATE_TIME")
    private LocalDateTime departureDateTime;

    @Column(name = "ARRIVAL")
    private String arrival;

    @Column(name = "ARRIVAL_DATE_TIME")
    private LocalDateTime arrivalDateTime;


    @Column(name = "PRICE")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;

}