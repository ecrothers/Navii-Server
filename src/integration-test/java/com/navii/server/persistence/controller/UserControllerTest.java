package com.navii.server.persistence.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.navii.server.Application;
import com.navii.server.persistence.domain.User;
import com.navii.server.util.ObjectMapperFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Random;

/**
 * Created by JMtorii on 2015-11-27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(randomPort = true)
public class UserControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);
    private MockMvc mvc;
    private ObjectMapper objectMapper;
    private Random random;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        objectMapper = ObjectMapperFactory.createMapper();
        random = new Random();
    }

    @Test
    public void createUserFailsDueToInsufficientData() throws Exception {
        User user = new User.Builder()
                .username("userDoesNotExist")
                .build();

        sendCreateUserRequest(user)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void getUserFailsDueToMissingUser() throws Exception {
        getUserRequest(1234567890)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void createAndGetUserSucceeds() throws Exception {
        int randomId = random.nextInt(1000);

        User user = new User.Builder()
                .username("user-test_" + randomId)
                .password("password-test_" + randomId)
                .salt("salt-test_" + randomId)
                .isFacebook(false)
                .build();

        sendCreateUserRequest(user)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    private ResultActions getUserRequest(int userId) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.get(String.format("/user/%s", userId)));
    }

    private ResultActions sendCreateUserRequest(User user) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.post("/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)));
    }
}
