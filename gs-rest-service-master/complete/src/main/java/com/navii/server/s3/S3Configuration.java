package com.navii.server.s3;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3Client;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.navii.server.aws.AWSConfiguration;
import com.navii.server.util.ObjectMapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * Created by JMtorii on 15-10-13.
 */
@Configuration
@Import(AWSConfiguration.class)
public class S3Configuration {
    static Logger log = LoggerFactory.getLogger(S3Configuration.class);

    /**
     * If false, will use the local filesystem to simulate S3.
     */
    @Value("${amazon.s3.enabled:true}")
    private boolean s3Enabled;

    /**
     * The bucket that this DAO will store documents in.
     */
    @Value("${amazon.s3.bucket:changeme}")
    private String bucketName;
    
    /**
     * Indicates whether we will try to create a bucket if it does not exist. This needs to be set to false, for instance, in a prod
     * env where Ops wants to retain control over bucket creation.
     */
    @Value("${amazon.create.bucket:false}")
    private boolean doCreateBucket;

    @Bean
    public ObjectMapper objectMapper() {
        return ObjectMapperFactory.createMapper();
    }

    @Bean
    public AmazonS3Client createAmazonS3Client(AWSCredentialsProvider awsCredentialsProvider) {
        return new AmazonS3Client(awsCredentialsProvider);
    }

    @Bean
    public S3DAO createS3DAO(ObjectMapper objectMapper, AmazonS3Client s3Client) {
        log.info("S3 Enabled: " + s3Enabled);
        return (s3Enabled) ?
                new S3DAOImpl(objectMapper, s3Client, bucketName, doCreateBucket) :
                new LocalS3DAOImpl(objectMapper);
    }
}
