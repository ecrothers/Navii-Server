package com.navii.server.s3;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
public class LocalS3DAOImplTest {

    @Autowired
    private ObjectMapper objectMapper;

    private LocalS3DAOImpl localS3Dao;

    @Before
    public void setup() {
        localS3Dao = new LocalS3DAOImpl(objectMapper);
        localS3Dao.setup();
    }

    @Test
    public void localTest() {
        String objId = "123";
        SomeTestObject testObject = new SomeTestObject("foo", "bar");
        localS3Dao.save(objId, testObject);

        SomeTestObject fetchedObject = localS3Dao.load(SomeTestObject.class, objId);
        assertEquals(testObject.key, fetchedObject.key);
        assertEquals(testObject.value, fetchedObject.value);

        localS3Dao.delete(testObject.getClass(), objId);

        SomeTestObject shouldBeDeleted = localS3Dao.load(testObject.getClass(), objId);
        assertNull(shouldBeDeleted);
    }

    @S3DocumentType("SomeTestObject")
    private static class SomeTestObject {
        @JsonProperty
        String key;

        @JsonProperty
        String value;

        /**
         * For Jackson
         */
        @SuppressWarnings("unused")
        public SomeTestObject() {}

        public SomeTestObject(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}
