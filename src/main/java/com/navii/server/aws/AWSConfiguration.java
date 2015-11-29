package com.navii.server.aws;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by JMtorii on 15-10-13.
 */
@Configuration
public class AWSConfiguration {
    
    static Logger log = LoggerFactory.getLogger(AWSConfiguration.class);
    
    /**
     * This is the name of the AWS credentials profile to use when using a credential amazon.credentials.provider.type of aws_profile. The profile
     * name is used to find the AWS key and secret in ~/.aws/credentials.
     */
    @Value("${amazon.profile.name:default}")
    private String awsProfileName;

    /**
     * Indicates which method to use for providing AWS credentials.
     * aws_profile means we will look up credentials in ~/.aws/credentials.
     * instance_profile means we will retrieve temporary credentials from the instance profile of the EC2 instance we are running in. instance_profile 
     * can only be used when the app is running in EC2, as is the case with int and prod envs. 
     * 
     * @see CredentialsProviderType
     */
    @Value("${amazon.credentials.provider.type:aws_profile}")
    String credentialsProviderType;
    
    @Bean
    public AWSCredentialsProvider createAWSCredentialsProvider() {
        CredentialsProviderType credentialProvider = CredentialsProviderType.valueOf(credentialsProviderType);
        switch (credentialProvider) {
            case instance_profile:
                log.info("Using EC2 instance profile for AWS credentials.");
                return new InstanceProfileCredentialsProvider();
            case aws_profile:
                log.info("Using AWS profile: " + awsProfileName);
                return new ProfileCredentialsProvider(awsProfileName);
            default:
                throw new IllegalArgumentException("Unsupported credential provider type.");
        }
    }
}
