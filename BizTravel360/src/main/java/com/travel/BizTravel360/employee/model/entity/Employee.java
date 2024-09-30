package com.travel.BizTravel360.employee.model.entity;

import com.travel.BizTravel360.common.model.entity.BaseEntity;
import com.travel.BizTravel360.employee.enumEmployee.PositionEmployee;
import com.travel.BizTravel360.employee.enumEmployee.RoleEmployee;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "employee")
@NoArgsConstructor
@TableGenerator(name = "id_gen", initialValue = 3, allocationSize = 40,
        table = "id_gen", pkColumnName = "ENTITY", pkColumnValue = "employee", valueColumnName = "NEXT_ID")
public class Employee extends BaseEntity {
    
    @Column(name = "FIRST_NAME")
    private String firstName;
    
    @Column(name = "LAST_NAME")
    private String lastName;
    
    @Column(name = "POSITION")
    @Enumerated(EnumType.STRING)
    private PositionEmployee position;
    
    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private RoleEmployee role;
    
    @Column(name = "EMAIL")
    private String email;
    
    @Column(name = "PASSWORD")
    private String password;
    
    @Column(name = "TOKEN")
    private String token;
}
