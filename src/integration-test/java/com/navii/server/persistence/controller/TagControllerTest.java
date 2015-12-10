package com.navii.server.persistence.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.navii.server.Application;
import com.navii.server.persistence.domain.Tag;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JMtorii on 2015-12-03.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(randomPort = true)
@TestPropertySource(properties = "spring.datasource.url=jdbc:mysql://naviappdbinstance.cmd4kpxqni0s.us-east-1.rds.amazonaws.com:3306/naviDB_test")
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

    @Test
    public void getTagsReturnsEmptyListIfThereAreNoTags() throws Exception {
        sendDeleteTagsRequest()
                .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = sendGetTagsRequest()
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<String> tags = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<String>>() {
                });

        Assert.assertEquals(0, tags.size());
    }

    @Test
    public void getTagsReturnsListIfLessThan20Limit() throws Exception {
        sendDeleteTagsRequest()
                .andExpect(MockMvcResultMatchers.status().isOk());

        String[] tagsList = {"Cheese", "Pepperoni", "Black", "Copper", "a", "b", "c", "d", "Jesus", "God",
                "apple", "Bell", "Banana", "Telephone", "Mouse", "Mickey", "Allah", "Tim", "Hortons"};

        for (String tag : tagsList) {
            sendCreateTagRequest(new Tag.Builder().tag(tag).build())
                    .andExpect(MockMvcResultMatchers.status().isCreated());
        }

        MvcResult result = sendGetTagsRequest()
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<String> tags = objectMapper
                .readValue(result.getResponse().getContentAsString(), new TypeReference<List<String>>() {
                });
        Assert.assertEquals(tagsList.length, tags.size());
    }

    @Test
    public void getTagsAlwaysReturnsRandomList() throws Exception {
        String[] tagsList = {"Cheese", "Pepperoni", "Black", "Copper", "a", "b", "c", "d", "Jesus", "God",
                "apple", "Bell", "Banana", "Telephone", "Mouse", "Mickey", "Allah", "Tim", "Hortons", "Christ",
                "e", "f", "g"};

        sendDeleteTagsRequest()
                .andExpect(MockMvcResultMatchers.status().isOk());

        for (String tag : tagsList) {
            sendCreateTagRequest(new Tag.Builder().tag(tag).build())
                    .andExpect(MockMvcResultMatchers.status().isCreated());
        }

        MvcResult result = sendGetTagsRequest()
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<String> tags1 = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<String>>() {
        });

        result = sendGetTagsRequest()
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        List<String> tags2 = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<String>>() {
        });

        Assert.assertEquals(tags1.size(), tags2.size());

        Assert.assertThat(tags1, Matchers.not(Matchers.equalTo(tags2)));
    }

    @Test
    public void getTagsAlwaysReturns20Max() throws Exception {
        String[] tagsList = {"Cheese", "Pepperoni", "Black", "Copper", "a", "b", "c", "d", "Jesus", "God",
                "apple", "Bell", "Banana", "Telephone", "Mouse", "Mickey", "Allah", "Tim", "Hortons", "Christ",
                "e", "f", "g"};

        sendDeleteTagsRequest()
                .andExpect(MockMvcResultMatchers.status().isOk());

        for (String tag : tagsList) {
            sendCreateTagRequest(new Tag.Builder().tag(tag).build())
                    .andExpect(MockMvcResultMatchers.status().isCreated());
        }

        MvcResult result = sendGetTagsRequest()
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<String> tags1 = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<String>>() {
        });

        Assert.assertEquals(20, tags1.size());
    }

    private ResultActions sendGetTagsRequest() throws Exception {
        return mvc.perform(MockMvcRequestBuilders.get("/tags"));
    }

    private ResultActions sendDeleteTagsRequest() throws Exception {
        return mvc.perform(MockMvcRequestBuilders.delete("/tags/all"));
    }

    private ResultActions sendCreateTagRequest(Tag tag) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.post("/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tag)));
    }
}
