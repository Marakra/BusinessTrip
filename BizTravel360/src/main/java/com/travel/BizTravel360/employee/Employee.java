package com.travel.BizTravel360.employee;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.travel.BizTravel360.delegation.Delegation;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long employeeId;

    @NotBlank(message = "First name is a required field!")
    @Size(min = 3, max = 50, message = "First name must be between 3 and 50 characters")
    private String firstName;
    
    @NotBlank(message = "Last name is a required field!")
    @Size(min = 3, max = 50, message = "Last name must be between 3 and 50 characters")
    private String lastName;
    
    @NotNull(message = "Position is a required field!")
    @Enumerated(EnumType.STRING)
    private PositionEmployee position;
    
    private RoleEmployee role;
    
    @NotBlank(message = "Email is a required field!")
    @Email(message = "Email should be valid")
    private String email;
    
    private String password;
    
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Delegation> delegations;
    
    @PastOrPresent(message = "Create date must be in the past or present")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createDateTime = LocalDateTime.now();
    
    @PastOrPresent(message = "Update date must be in the past or present")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updateDateTime = LocalDateTime.now();
    
    public Employee() {}
    
    public Employee(Long employeeId, String firstName, String lastName, PositionEmployee position,
                    RoleEmployee role, String email, String password, List<Delegation> delegations) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.role = role;
        this.email = email;
        this.password = password;
        this.delegations = delegations;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(employeeId, employee.employeeId)
                && Objects.equals(firstName, employee.firstName)
                && Objects.equals(lastName, employee.lastName)
                && position == employee.position
                && role == employee.role
                && Objects.equals(email, employee.email)
                && Objects.equals(password, employee.password)
                && Objects.equals(delegations, employee.delegations);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(employeeId, firstName, lastName, position, role, email, password, delegations);
    }
}
