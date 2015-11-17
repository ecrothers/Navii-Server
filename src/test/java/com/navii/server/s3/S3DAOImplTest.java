package com.navii.server.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.navii.server.util.ObjectMapperFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by JMtorii on 2015-11-15.
 */
public class S3DAOImplTest {
    private ObjectMapper objectMapper;
    private S3DAOImpl s3DAO;

    @Before
    public void setup() {
        objectMapper = ObjectMapperFactory.createMapper();
        AmazonS3Client s3Client = new AmazonS3Client();
        s3DAO = new S3DAOImpl(objectMapper, s3Client, "navi.ci.test", false, "ci");
        s3DAO.setup();
    }

    @Test
    public void saveAndLoadTest() {
        S3TestHelper.saveAndLoadTest(s3DAO, "foo", "bar");
    }
}
