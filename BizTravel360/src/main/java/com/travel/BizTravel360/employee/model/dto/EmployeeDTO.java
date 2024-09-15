package com.travel.BizTravel360.employee.model.dto;

import com.travel.BizTravel360.employee.PositionEmployee;
import com.travel.BizTravel360.employee.RoleEmployee;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class EmployeeDTO {
    private Long id;
    
    @NotBlank(message = "First name is a required field!")
    @Size(min = 3, max = 50, message = "First name must be between 3 and 50 characters")
    private String firstName;
    
    @NotBlank(message = "Last name is a required field!")
    @Size(min = 3, max = 50, message = "Last name must be between 3 and 50 characters")
    private String lastName;
    
    @NotNull(message = "Position is a required field!")
    private PositionEmployee position;
    
    @NotBlank(message = "Email is a required field!")
    @Email(message = "Email should be valid")
    private String email;
    
    @NotBlank(message = "Password is a required field!")
    @Size(min = 6, message = "Password must be longer than 6 characters")
    private String password;
    
}
