package com.navii.server.s3;

import com.fasterxml.jackson.annotation.JsonProperty;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by JMtorii on 2015-11-16.
 */
public class S3TestHelper {
    public static void saveAndLoadTest(S3DAO s3DAO, String key, String value) {
        String objId = "123";
        SomeTestObject someTestObject = new SomeTestObject(key, value);
        s3DAO.save(objId, someTestObject);

        SomeTestObject fetchedObject = s3DAO.load(SomeTestObject.class, objId);
        assertEquals(someTestObject.key, fetchedObject.key);
        assertEquals(someTestObject.value, fetchedObject.value);
    }

    public static void deleteTest(S3DAO s3DAO, String key, String value) {
        String objId = "123";
        SomeTestObject someTestObject = new SomeTestObject(key, value);
        s3DAO.delete(someTestObject.getClass(), objId);

        SomeTestObject shouldBeDeleted = s3DAO.load(someTestObject.getClass(), objId);
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
