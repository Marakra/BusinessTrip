package com.travel.BizTravel360.person;

public class PersonService {
    
    private final PersonRepository personRepository;
    
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    
    public void getAllPersons(){
        System.out.println(personRepository.findAll());
    }
    
}
