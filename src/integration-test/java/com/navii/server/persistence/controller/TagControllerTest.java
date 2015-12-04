package com.navii.server.persistence.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.navii.server.Application;
import com.navii.server.util.ObjectMapperFactory;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/**
 * Created by JMtorii on 2015-12-03.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(randomPort = true)
public class TagControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(TagControllerTest.class);

    private MockMvc mvc;
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        objectMapper = ObjectMapperFactory.createMapper();
    }

    @Ignore
    @Test
    public void getTagsReturnsEmptyListIfThereAreNoTags() throws Exception {
        MvcResult result = sendGetTagsRequest()
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<String> tags = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<String>>(){});

        // TODO: clear the table data before doing this assert
        Assert.assertEquals(0, tags.size());
    }

    @Ignore
    @Test
    public void getTagsReturnsListIfLessThan20Limit() throws Exception {
        // TODO: implement me once deleteAll is implemented
    }

    @Test
    public void getTagsAlwaysReturnsRandomList() throws Exception {
        MvcResult result = sendGetTagsRequest()
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<String> tags1 = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<String>>(){});

        result = sendGetTagsRequest()
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        List<String> tags2 = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<String>>(){});

        Assert.assertEquals(tags1.size(), tags2.size());

        // TODO: very risky shit here. There may be a rare case that it returns the same list of tags in the same order
        Assert.assertThat(tags1, Matchers.not(Matchers.equalTo(tags2)));
    }

    private ResultActions sendGetTagsRequest() throws Exception {
        return mvc.perform(MockMvcRequestBuilders.get("/tags"));
    }
}
