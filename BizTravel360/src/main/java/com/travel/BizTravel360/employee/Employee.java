package com.travel.BizTravel360.employee;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.travel.BizTravel360.common.model.entity.BaseEntity;
import com.travel.BizTravel360.delegation.Delegation;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@TableGenerator(name = "id_gen", initialValue = 3, allocationSize = 40,
        table = "id_gen", pkColumnName = "ENTITY", pkColumnValue = "employee", valueColumnName = "NEXT_ID")
public class Employee extends BaseEntity {
    
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
}
