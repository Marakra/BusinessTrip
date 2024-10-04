package com.travel.BizTravel360.employee.component;

import com.travel.BizTravel360.employee.domain.EmployeeRepository;
import com.travel.BizTravel360.employee.model.entity.Employee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class EmployeeDetailsService implements UserDetailsService {
    
    private final EmployeeRepository employeeRepository;
    
    public EmployeeDetailsService(EmployeeRepository employeeRepository) {this.employeeRepository = employeeRepository;}
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(username);
        
        if (!optionalEmployee.isPresent()) {
            throw new UsernameNotFoundException(String.format("Not found employee with username: %s", username));
        }
        Employee employee = optionalEmployee.get();
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
        
        return new User(employee.getEmail(), employee.getPassword(), grantedAuthorities);
    }
}
