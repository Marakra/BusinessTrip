package com.travel.BizTravel360.person;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository {
    
    void createPerson(Person person) throws Exception;
    List<Person> readAll() throws Exception;
}
