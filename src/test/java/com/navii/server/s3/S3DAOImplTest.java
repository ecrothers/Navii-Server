package com.navii.server.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.navii.server.util.ObjectMapperFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by JMtorii on 2015-11-15.
 */
public class S3DAOImplTest {
    private ObjectMapper objectMapper;
    private S3DAOImpl s3DAO;
    private SomeTestObject someTestObject;

    @Before
    public void setup() {
        objectMapper = ObjectMapperFactory.createMapper();
        AmazonS3Client s3Client = new AmazonS3Client();
        s3DAO = new S3DAOImpl(objectMapper, s3Client, "navi.ci.test", false, "ci");
        s3DAO.setup();

        someTestObject = new SomeTestObject("foo", "bar");
    }

    @Test
    public void saveAndLoadTest() {
        String objId = "123";
        s3DAO.save(objId, someTestObject);

        SomeTestObject fetchedObject = s3DAO.load(SomeTestObject.class, objId);
        assertEquals(someTestObject.key, fetchedObject.key);
        assertEquals(someTestObject.value, fetchedObject.value);
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
