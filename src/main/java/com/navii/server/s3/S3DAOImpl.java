package com.navii.server.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.util.IOUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by JMtorii on 15-10-13.
 */
// TODO: Handle caching
public class S3DAOImpl implements S3DAO {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(S3DAOImpl.class);

    /**
     * Amazon returns this error code when you try to load a file that doesn't exist
     */
    private static final String NO_SUCH_KEY = "NoSuchKey";

    private final ObjectMapper objectMapper;
    private final AmazonS3Client s3Client;
    private final String bucketName;
    private final String envPrefix;
    private final boolean createBucket;

    public S3DAOImpl(ObjectMapper objectMapper, AmazonS3Client s3Client, String bucketName, boolean createBucket, String envPrefix) {
        this.objectMapper = objectMapper;
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.createBucket = createBucket;
        this.envPrefix = envPrefix;
    }

    @PostConstruct
    public void setup() {
        createBucketIfNecessary();
    }

    @Override
    public <T> void save(String id, T obj) {
        save(id, obj.getClass().getAnnotation(S3DocumentType.class).value(), obj);
    }

    @Override
    public <T> void save(String id, String documentType, T obj) {
        String fileName = getFileName(id, documentType);
        logger.debug("Put content file {} to bucket {}", fileName, bucketName);
        byte[] bytes = objectToByteArray(obj);
        try (InputStream inputStream = createInputStream(bytes)) {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(bytes.length);
            s3Client.putObject(bucketName, fileName, inputStream, objectMetadata);
        } catch (Exception e) {
            logger.warn("Caught exception while trying to put file {} into bucket {}", fileName, bucketName, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T load(Class<T> clazz, String id) {
        return load(clazz, id, clazz.getAnnotation(S3DocumentType.class).value());
    }

    @Override
    public <T> T load(Class<T> clazz, String id, String documentType) {
        String fileName = getFileName(id, documentType);

        logger.debug("Getting file {} from bucket {}", fileName, bucketName);
        try (InputStream inputStream = s3Client.getObject(bucketName, fileName).getObjectContent()) {
            if (inputStream != null) {
                return getFileContent(clazz, inputStream);
            }
            return null;

        } catch (AmazonServiceException e) {
            if (NO_SUCH_KEY.equals(e.getErrorCode())) {
                // File not found in S3
                return null;
            }
            throw e;
        } catch (IOException e) {
            throw new RuntimeException("Error reading content from file " + fileName, e);
        } catch (RuntimeException e) {
            logger.warn("Caught exception while trying to get file {} from bucket {}", fileName, bucketName, e);
            throw e;
        }
    }

    @Override
    public <T> void delete(Class<T> clazz, String id) {
        delete(id, clazz.getAnnotation(S3DocumentType.class).value());
    }

    @Override
    public void delete(String id, String documentType) {
        s3Client.deleteObject(bucketName, getFileName(id, documentType));
    }

    private void createBucketIfNecessary() {
        if (!createBucket) {
            return;
        }
        logger.info("Checking if bucket {} exists..", bucketName);
        // check to see if bucket exists
        if (!s3Client.doesBucketExist(bucketName)) {
            logger.info("Bucket {} does not exists. Creating it..", bucketName);
            s3Client.createBucket(bucketName);
            return;
        }

        logger.info("Bucket {} already exists.", bucketName);
    }

    private <T> byte[] objectToByteArray(T obj) {
        try {
            return objectMapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            logger.warn("Caught exception while trying to serialize to input stream", e);
            throw new RuntimeException(e);
        }
    }

    private InputStream createInputStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }

    private String getFileName(String documentId, String documentType) {
        return String.format("%s.%s", documentId, documentType);
    }

    private <T> T getFileContent(Class<T> clazz, InputStream inputStream) throws IOException {
        return objectMapper.readValue(IOUtils.toString(inputStream), clazz);
    }
}
