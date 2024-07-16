package com.travel.BizTravel360.person;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;


@Setter
@Getter
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long personId;

    @NotEmpty(message = "First name is a required field!")
    @Size(min = 2, message = "First name should contain at least 2 characters.")
    private String firstName;
    
    @NotEmpty(message = "Last name is a required field!")
    @Size(min = 2, message = "Last name should contain at least 2 characters")
    private String lastName;
    
    @NotEmpty(message = "Email is a required field!")
    @Email(message = "Email should be valid")
    private String email;
    
    public Person(long personId, String firstName, String lastName, String email) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    
    public Person() {}
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return personId == person.personId && Objects.equals(firstName, person.firstName)
                            && Objects.equals(lastName, person.lastName)
                            && Objects.equals(email, person.email);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(personId, firstName, lastName, email);
    }
    
    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
