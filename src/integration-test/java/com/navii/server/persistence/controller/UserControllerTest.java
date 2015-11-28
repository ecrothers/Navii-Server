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
        random = new Random();
        objectMapper = ObjectMapperFactory.createMapper();
    }

    @Test
    public void createAndGetUserSucceeds() throws Exception {
        // TODO: put range for value to be positive
        int randomId = random.nextInt();

        // TODO: add isfacebook
        User user = new User.Builder()
                .username("user-test_1")
                .password("password-test_1")
                .salt("salt-test_" + randomId)
                .build();

        sendCreateFlockRequest(user)
                .andExpect(MockMvcResultMatchers.status().isOk());


    }

    private ResultActions sendCreateFlockRequest(User user) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.post("/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)));
    }
}
