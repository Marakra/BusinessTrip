package com.travel.BizTravel360;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.BizTravel360.delegation.Delegation;
import com.travel.BizTravel360.file.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileServiceTest {
    
    private FileService fileService;
    private ObjectMapper objectMapper;
    private String testFilePath = "testFilePath.json";
    
    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        fileService = new FileService(objectMapper);
    }
    
    @Test
    void testWriterAndReaderFromFile() throws IOException {
        Delegation delegation = new Delegation();
        delegation.setNameDelegation("TestDelegation");
        List<Delegation> delegationList = List.of(delegation);
        
        fileService.writerToFile(delegationList, testFilePath);
        List<Delegation> readDelegations = fileService.readFromFile(testFilePath, new TypeReference<List<Delegation>>() {});
        
        assertEquals(1, readDelegations.size());
        assertEquals("TestDelegation", readDelegations.get(0).getNameDelegation());
        
        Files.deleteIfExists(Paths.get(testFilePath));
    }
}
