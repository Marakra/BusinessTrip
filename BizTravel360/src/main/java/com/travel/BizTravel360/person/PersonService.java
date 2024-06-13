package com.travel.BizTravel360.person;

import com.travel.BizTravel360.person.Person;
import com.travel.BizTravel360.person.PersonRepository;

import java.util.List;

public class PersonService implements PersonRepository {
    
    @Override
    public List<Person> findAll() {
        return List.of();
    }
    
}
