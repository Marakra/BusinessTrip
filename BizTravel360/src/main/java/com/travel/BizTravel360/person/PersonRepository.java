package com.travel.BizTravel360.person;

import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public  interface PersonRepository {
    
    Person savePerson(Person person) throws IOException;
    List<Person> fetchPeopleList();
    Person updatePerson(Person person, Long personId) throws IOException;
    void deletePersonById(Long personId) throws IOException;
    Person findPersonById(Long personId) throws IOException;
    
}
