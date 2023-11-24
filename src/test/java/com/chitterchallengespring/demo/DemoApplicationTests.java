package com.chitterchallengespring.demo;

import com.chitterchallengespring.demo.helpers.JsonFileReader;
import com.chitterchallengespring.demo.model.Peep;
import com.chitterchallengespring.demo.model.User;
import com.chitterchallengespring.demo.repositories.PeepRepository;
import com.chitterchallengespring.demo.services.PeepService;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONValue;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void clearCollection() {
        TestMongoConfig.clearCollection();
    }


    @Nested
    @DisplayName("Peep tests")
    class PeepTests {
        @Test
        @DisplayName("Should return status code 200 when all peeps are found")
        void shouldReturnOKHttpStatusCode() throws Exception {
            mockMvc.perform(get("/peeps"))
                    .andExpect(status().isOk());
        }


        @Test
        @DisplayName("Should return status code 201 when a correctly formatted peep is added")
        void shouldReturnOKStatusCode() throws Exception {
            Peep testPeep = new Peep();
            testPeep.setUserID("John2000");
            testPeep.setTime("2023-08-12T08:00:00.000Z");
            testPeep.setMessage("Test Peep!");
            System.out.println(testPeep);
            mockMvc.perform(
                    post("/addPeep").contentType(MediaType.APPLICATION_JSON)
                            .content(JSONValue.toJSONString(testPeep))).andExpect(status().is(201));
        }

        @Test
        @DisplayName("Should return bad request if an incorrectly formatted peep is added")
        void shouldReturnNotOKHttpStatusCode() throws Exception {
            Peep testPeep = new Peep();
            testPeep.setUserID("John2000");
            testPeep.setTime("2023-08-12T08:00:00.000Z");
//			testPeep.setMessage("Test Peep!");
            System.out.println(testPeep);
            mockMvc.perform(
                    post("/addPeep").contentType(MediaType.APPLICATION_JSON)
                            .content(JSONValue.toJSONString(testPeep))).andExpect(status().is(400));
        }


        @Test
        @DisplayName("Should return status code 200 when you try to edit/update an existing peep")
        void shouldReturnOkHttpStatusCode() throws Exception {
            Peep testPeep = new Peep();
            testPeep.setUserID("John2000");
            testPeep.setTime("2023-08-12T08:00:00.000Z");
            testPeep.setMessage("Test Peep!");

            MvcResult result =
                    mockMvc.perform(
                                    post("/addPeep")
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .content(JSONValue.toJSONString(testPeep)))
                            .andExpect(status().is(201)).andReturn();

            String contentAsString = result.getResponse().getContentAsString();

            ObjectMapper objectMapper = new ObjectMapper();
            Peep responsePeep = objectMapper.readValue(contentAsString, Peep.class);

            responsePeep.setMessage("I'm the mega updated final tweet.");


            mockMvc.perform(
                            put("/" + responsePeep.getId())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(JSONValue.toJSONString(responsePeep)))
                    .andExpect(status().is(200));
        }

    }

    // USER TESTS
    @Nested
    @DisplayName("User tests")
    class UserTests {

        @Test
        @DisplayName("Should get ok status code if a user signs up with all the required fields.")
        void shouldReturnOkHttpStatusCode() throws Exception {
            User testUser = new User();
            testUser.setName("Mario");
            testUser.setUsername("Mario2023");
            testUser.setEmail("Mario@hotmail.com");
            testUser.setPassword("mario");

            mockMvc.perform(post("/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JSONValue.toJSONString(testUser))).andExpect(status().is(201));
        }

        @Test
        @DisplayName("Should get a bad request status code if a user does not sign up with all required fields.")
        void shouldReturnBadRequestHttpStatusCode() throws Exception {
            User testUser = new User();
//          testUser.setName("Mario");
            testUser.setUsername("Mario2023");
            testUser.setEmail("Mario@hotmail.com");
            testUser.setPassword("mario");

            mockMvc.perform(post("/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JSONValue.toJSONString(testUser))).andExpect(status().is(400));
        }

        @Test
        @DisplayName("Should return an okay status code if a registered user signs in.")
        void shouldReturnOkStatusCode() throws Exception {
            User testUser = new User();
            testUser.setName("Mario");
            testUser.setUsername("Mario2023");
            testUser.setEmail("Mario@hotmail.com");
            testUser.setPassword("mario");

            MvcResult result = mockMvc.perform(post("/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JSONValue.toJSONString(testUser))).andExpect(status().is(201)).andReturn();

            String contentAsString = result.getResponse().getContentAsString();
            System.out.println(contentAsString);

            ObjectMapper objectMapper = new ObjectMapper();
            User userResponse = objectMapper.readValue(contentAsString, User.class);

            mockMvc.perform(post("/login/Mario2023")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JSONValue.toJSONString(testUser))).andExpect(status().is(200));
        }

        @Test
        @DisplayName("Should return a not found status code if a user who is not registered tries to sign in.")
        void shouldReturnNotFoundStatusCode() throws Exception {
            mockMvc.perform(post("/login/NotRegisteredUser")).andExpect(status().is(400));
        }

    }


}
