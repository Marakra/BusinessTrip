package com.travel.BizTravel360.employee.enumEmployee;

import org.springframework.security.core.GrantedAuthority;

public enum RoleEmployee implements GrantedAuthority {
    ROLE_ADMIN,
    ROLE_MANAGER,
    ROLE_HR,
    ROLE_EMPLOYEE;
    
    @Override
    public String getAuthority() {
        return name();
    }
}
