package com.travel.BizTravel360.Person;

import java.util.List;

public class PersonService {
    
    private final PersonRepository personRepository;
    
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    
    public void getAllPersons(){
        System.out.println(personRepository.findAll());
    }
    
}
