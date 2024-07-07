package com.travel.BizTravel360.person;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public  interface PersonRepository {
    
    Person savePerson(Person person);
    List<Person> fetchPeopleList();
    Person updatePerson(Person person, Long personId);
    void deletePersonById(Long personId);
    
}
