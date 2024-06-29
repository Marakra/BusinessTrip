package com.travel.BizTravel360.person;

import com.fasterxml.jackson.core.type.TypeReference;
import com.travel.BizTravel360.file.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class PersonService implements PersonRepository {
    
    private final FileService fileService;
    
    @Value("${people.file.path}")
    private String peopleFilePath;
    
    public PersonService(FileService fileService) {
        this.fileService = fileService;
    }
    
    @Override
    public void createPerson(Person person) throws IOException {
        List<Person> people = readAll();
        long maxId = people.stream().mapToLong(Person::getPersonId).max().orElse(0);
        person.setPersonId(maxId + 1);
        people.add(person);
        fileService.writerToFile(people, peopleFilePath);
    }
    
    @Override
    public List<Person> readAll() throws IOException {
        return fileService.readFromFile(new TypeReference<List<Person>>() {}, peopleFilePath);
    }
    
}
