package com.travel.BizTravel360.person;

import java.util.Objects;

public class Person {
    private long personId;
    private String firstName;
    private String lastName;
    private String email;
    
    public Person() {}
    
    public Person(long personId, String firstName, String lastName, String email) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    
    public long getPersonId() {
        return personId;
    }
    
    public void setPersonId(long personId) {
        this.personId = personId;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
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
}
