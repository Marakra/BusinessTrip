package com.travel.BizTravel360.employee.model.dto;

import com.travel.BizTravel360.employee.enumEmployee.PositionEmployee;
import com.travel.BizTravel360.employee.component.RandomStringToGenerateNameToken;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class EmployeeDTO {
    private Long id;
    
    @Size(min = 3, max = 50, message = "First name must be between 3 and 50 characters in length.")
    private String firstName;
    
    @Size(min = 3, max = 50, message = "Last name must be between 3 and 50 characters in length")
    private String lastName;
    
    private PositionEmployee position;
    
    @Email(message = "Email should be valid")
    private String email;
    
    private String password;
    private String token;
    
    public EmployeeDTO() {
        this.token = RandomStringToGenerateNameToken.generateRandomNameToken();
    }
}
