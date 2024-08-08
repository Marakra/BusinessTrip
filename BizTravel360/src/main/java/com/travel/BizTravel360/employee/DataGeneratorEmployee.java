package com.travel.BizTravel360.employee;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataGeneratorEmployee {
    
    private static final Random RANDOM = new Random();
    
    public static List<Employee> generateRandomEmployeesList(int count) {
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            employees.add(generateRandomEmployee());
        }
        return employees;
    }
    
    private static Employee generateRandomEmployee() {
        Employee employee = new Employee();
        employee.setEmployeeId((long) RANDOM.nextLong(100000));
        employee.setFirstName("First Name" + RANDOM.nextInt(1000));
        employee.setLastName("Last Name" + RANDOM.nextInt(1000));
        
        String randomEmail = generateRandomEmail(employee.getFirstName(), employee.getLastName());
        employee.setEmail(randomEmail);
        return employee;
    }
    
    private static String generateRandomEmail(String firstName, String lastName) {
        String[] domains = {"example.com", "test.com", "biztravel.com", "email.com"};
        String domain = domains[RANDOM.nextInt(domains.length)];
        String email = String.format("%s.%s%d@%s", firstName.toLowerCase(), lastName.toLowerCase(), RANDOM.nextInt(1000), domain);
        return email;
    }
}
