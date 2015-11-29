package com.navii.server.s3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.navii.server.util.ObjectMapperFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by JMtorii on 2015-11-16.
 */
public class LocalS3DAOImplTest {
    private ObjectMapper objectMapper;
    private LocalS3DAOImpl localS3Dao;

    @Before
    public void setup() {
        objectMapper = ObjectMapperFactory.createMapper();
        localS3Dao = new LocalS3DAOImpl(objectMapper, "ci");
        localS3Dao.setup();
    }

    @Test
    public void saveAndLoadTest() {
        S3TestHelper.saveAndLoadTest(localS3Dao, "foo", "bar");
    }

    @Test
    public void deleteTest() {
        S3TestHelper.deleteTest(localS3Dao, "foo", "bar");
    }
}
