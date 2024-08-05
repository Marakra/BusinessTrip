package com.travel.BizTravel360.delegation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.travel.BizTravel360.delegation.annotation.ValidDateRangeDelegation;
import com.travel.BizTravel360.accommodation.Accommodation;
import com.travel.BizTravel360.employee.Employee;
import com.travel.BizTravel360.transport.Transport;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@Entity
@ValidDateRangeDelegation
public class Delegation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long delegationId;
    
    @NotBlank(message = "Name delegation is a required field!")
    @Size(min = 3, max = 50, message = "Name delegation must be between 3 and 50 characters")
    private String nameDelegation;
    
    @NotNull(message = "Employee is a required field!")
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    
    @NotNull(message = "Transport list is a required field!")
    @OneToMany(mappedBy = "delegation", cascade = CascadeType.ALL)
    private List<Transport> transports;
    
    @NotNull(message = "Accommodation list is a required field!")
    @OneToMany(mappedBy = "delegation", cascade = CascadeType.ALL)
    private List<Accommodation> accommodations;
    
    @Digits(integer = 5, fraction = 2, message = "Invalid format. Max 5 digits and 2 decimals.")
    private Double totalPrice;
    
    private Boolean isAccepted = true;
    
    @NotNull(message = "Departure date is a required field!")
    @FutureOrPresent(message = "Departure date must be in the present or future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "Europe/Warsaw")
    private LocalDateTime departureDateTime;
    
    @NotNull(message = "Arrival date is a required field!")
    @Future(message = "Arrival date must be in the future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "Europe/Warsaw")
    private LocalDateTime arrivalDateTime;
    
    @NotNull(message = "Create date is a required field!")
    @PastOrPresent(message = "Create date must be in the past or present")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "Europe/Warsaw")
    private LocalDateTime createDateTime = LocalDateTime.now();
    
    @NotNull(message = "Update date is a required field!")
    @PastOrPresent(message = "Update date must be in the past or present")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "Europe/Warsaw")
    private LocalDateTime updateDateTime = LocalDateTime.now();
    
    public Delegation() {}
    
    public Delegation(Long delegationId, String nameDelegation, Employee employee,
                      List<Transport> transports, List<Accommodation> accommodations,
                      Double totalPrice, Boolean isAccepted, LocalDateTime departureDateTime,
                      LocalDateTime arrivalDateTime, LocalDateTime createDateTime, LocalDateTime updateDateTime) {
        this.delegationId = delegationId;
        this.nameDelegation = nameDelegation;
        this.employee = employee;
        this.transports = transports;
        this.accommodations = accommodations;
        this.totalPrice = totalPrice != null ? totalPrice : 0.0;
        this.isAccepted = isAccepted != null ? isAccepted : true;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.createDateTime = createDateTime != null ? createDateTime : LocalDateTime.now();
        this.updateDateTime = updateDateTime != null ? updateDateTime : LocalDateTime.now();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delegation that = (Delegation) o;
        return Objects.equals(delegationId, that.delegationId)
                && Objects.equals(nameDelegation, that.nameDelegation)
                && Objects.equals(employee, that.employee)
                && Objects.equals(transports, that.transports)
                && Objects.equals(accommodations, that.accommodations)
                && Objects.equals(totalPrice, that.totalPrice)
                && Objects.equals(isAccepted, that.isAccepted)
                && Objects.equals(departureDateTime, that.departureDateTime)
                && Objects.equals(arrivalDateTime, that.arrivalDateTime)
                && Objects.equals(createDateTime, that.createDateTime)
                && Objects.equals(updateDateTime, that.updateDateTime);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(delegationId, nameDelegation, employee, transports, accommodations, totalPrice,
                isAccepted, departureDateTime, arrivalDateTime, createDateTime, updateDateTime);
    }
}
