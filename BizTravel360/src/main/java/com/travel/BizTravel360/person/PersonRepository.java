package com.travel.BizTravel360.person;

import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Repository
public  interface PersonRepository {
    
    Person savePerson(Person person) throws IOException;
    List<Person> fetchPeopleList();
    Person updatePerson(Person person, UUID personUuid);
    void deletePersonById(UUID personUuid);
    Person findPersonByUuid(UUID personUuid);
    
}
