package com.navii.server.s3;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.navii.server.util.ObjectMapperFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class LocalS3DAOImplTest {

    private ObjectMapper objectMapper;

    private LocalS3DAOImpl localS3Dao;

    private SomeTestObject someTestObject;

    @Before
    public void setup() {
        objectMapper = ObjectMapperFactory.createMapper();
        localS3Dao = new LocalS3DAOImpl(objectMapper, "ci");
        localS3Dao.setup();

        someTestObject = new SomeTestObject("foo", "bar");
    }

    @Test
    public void saveAndLoadTest() {
        String objId = "123";
        localS3Dao.save(objId, someTestObject);

        SomeTestObject fetchedObject = localS3Dao.load(SomeTestObject.class, objId);
        assertEquals(someTestObject.key, fetchedObject.key);
        assertEquals(someTestObject.value, fetchedObject.value);
    }

    @Test
    public void deleteTest() {
        String objId = "123";
        localS3Dao.delete(someTestObject.getClass(), objId);

        SomeTestObject shouldBeDeleted = localS3Dao.load(someTestObject.getClass(), objId);
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
