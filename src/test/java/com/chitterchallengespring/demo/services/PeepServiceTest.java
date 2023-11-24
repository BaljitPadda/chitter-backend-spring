package com.chitterchallengespring.demo.services;

import com.chitterchallengespring.demo.dto.PeepResponseDTO;
import com.chitterchallengespring.demo.model.Peep;
import com.chitterchallengespring.demo.model.User;
import com.chitterchallengespring.demo.repositories.PeepRepository;
import com.chitterchallengespring.demo.repositories.UserRepository;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.verification.VerificationMode;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class PeepServiceTest {
    @Mock
    PeepRepository peepRepository;
    //PeepService peepService;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void shouldBeAbleToEditAnExistingPeep() throws Exception {

        PeepService peepService = new PeepService(peepRepository, null);

        Peep testPeep = new Peep();
        testPeep.setId("testID123");
        testPeep.setUserID("John2000");
        testPeep.setTime("2023-08-12T08:00:00.000Z");
        testPeep.setMessage("Test Peep!");

        when(peepRepository.existsById(testPeep.getId())).thenReturn(true);

        Peep updatedPeep = new Peep();
        updatedPeep.setUserID("John2000");
        updatedPeep.setTime("2023-08-12T08:00:00.000Z");
        updatedPeep.setMessage("Updated an existing peep!");

        when(peepRepository.save(updatedPeep)).thenReturn(updatedPeep);

        Peep result = peepService.editPeep(testPeep.getId(), updatedPeep);

        Assertions.assertEquals(updatedPeep.getMessage(), result.getMessage());

    }

    @Test
    void shouldReturnAnErrorIfTryingToEditAPeepThatDoesNotExist() throws Exception {

        PeepService peepService = new PeepService(peepRepository, null);

        String madeUpID = "MADEUPID123";

        when(peepRepository.existsById(anyString())).thenReturn(false);

        Peep peep = new Peep(); // placeholder peep created to pass in as the updated peep.

        Exception exception = Assertions.assertThrows(ResponseStatusException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                peepService.editPeep(madeUpID, peep);
            }
        });

        // when you want to test that an exception is thrown, you need to use executable, else the exeception terminates program.

        Assertions.assertEquals("404 NOT_FOUND \"That peep can't be found.\"", exception.getMessage());

    }


    @Test
    void shouldBeAbleToAddPeep() {
        PeepService peepService = new PeepService(peepRepository, null);
        Peep testPeep = new Peep();
        when(peepRepository.save(testPeep)).thenReturn(testPeep);

        Peep actual = peepService.addPeep(testPeep);

        Assertions.assertEquals(actual, testPeep);
        Mockito.verify(peepRepository).save(testPeep);

    }

    @Test
    void shouldBeAbleToGetAllPeeps() {

        PeepService peepService = new PeepService(peepRepository, userRepository);

        // Test Peep Data
        Peep testPeep1 = new Peep();
        testPeep1.setUserID("Matthew2023");
        testPeep1.setTime("2023-08-12T08:00:00.000Z");
        testPeep1.setMessage("test message 1");

        Peep testPeep2 = new Peep();
        testPeep2.setUserID("Mark2023");
        testPeep2.setTime("2023-08-12T08:00:00.000Z");
        testPeep2.setMessage("test message 2");

        Peep testPeep3 = new Peep();
        testPeep3.setUserID("Luke2023");
        testPeep3.setTime("2023-08-12T08:00:00.000Z");
        testPeep3.setMessage("test message 3");

        // Test User Data
        User testUser1 = new User();
        testUser1.setUsername("Matthew2023");
        testUser1.setName("Matthew");

        User testUser2 = new User();
        testUser2.setUsername("Mark2023");
        testUser2.setName("Mark");

        User testUser3 = new User();
        testUser3.setUsername("Luke2023");
        testUser3.setName("Luke");

        List<Peep> allPeeps = List.of(testPeep1, testPeep2, testPeep3);
        List<User> allUsers = List.of(testUser1, testUser2, testUser3);

        // Expected mapped response
        PeepResponseDTO testResponse1 = new PeepResponseDTO();
        testResponse1.setName("Matthew");
        testResponse1.setUserID("Matthew2023");
        testResponse1.setTime("2023-08-12T08:00:00.000Z");
        testResponse1.setMessage("test message 1");

        PeepResponseDTO testResponse2 = new PeepResponseDTO();
        testResponse2.setName("Mark");
        testResponse2.setUserID("Mark2023");
        testResponse2.setTime("2023-08-12T08:00:00.000Z");
        testResponse2.setMessage("test message 2");

        PeepResponseDTO testResponse3 = new PeepResponseDTO();
        testResponse3.setName("Luke");
        testResponse3.setUserID("Luke2023");
        testResponse3.setTime("2023-08-12T08:00:00.000Z");
        testResponse3.setMessage("test message 3");

        List<PeepResponseDTO> expected = List.of(testResponse1, testResponse2, testResponse3);

        // How I expect the mocked repositories to behave
        when(peepRepository.findAll(any(Sort.class))).thenReturn(allPeeps);
        when(userRepository.findAll()).thenReturn(allUsers);

        // Performing the getAllPeeps() test
        List<PeepResponseDTO> actual = peepService.getAllPeeps();

        // Checking that findAll() is called on both
        Mockito.verify(peepRepository).findAll(any(Sort.class));
        Mockito.verify(userRepository).findAll();

        // expecting to see an arraylist of type: List<PeepResponseDTO> which proves the private method mapping is also called.
        Assertions.assertTrue(actual.contains(testResponse1));
        Assertions.assertTrue(actual.contains(testResponse2));
        Assertions.assertTrue(actual.contains(testResponse3));


        Assertions.assertEquals(userRepository.findAll().size(), allUsers.size());








    }


}