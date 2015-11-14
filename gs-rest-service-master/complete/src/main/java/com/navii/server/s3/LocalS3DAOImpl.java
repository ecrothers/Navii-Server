package com.navii.server.s3;

import com.amazonaws.util.IOUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.io.*;

/**
 * Created by JMtorii on 15-10-16.
 */
public class LocalS3DAOImpl implements S3DAO {

    private final static Logger logger = LoggerFactory.getLogger(LocalS3DAOImpl.class);

    private static final String DEFAULT_TEMP_FILE_SUFFIX = ".txt";

    @Value("${env:}")
    private String envPrefix;

    private ObjectMapper objectMapper;

    private File dataDir;

    public LocalS3DAOImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void setup() {
        // save to user home
        String dataDirPath = System.getProperty("user.home") + File.separator + "localS3" + File.separator + envPrefix;
        logger.info("dataDirPath: " + dataDirPath);
        dataDir = new File(dataDirPath);

        if (!dataDir.exists()) {
            dataDir.mkdirs();
            logger.debug("Made localS3 folder at " + dataDir.getPath());
        }
    }

    @Override
    public <T> void save(String id, T obj) {
        save(id, obj.getClass().getAnnotation(S3DocumentType.class).value(), obj);
    }

    @Override
    public <T> void save(String id, String documentType, T obj) {
        String fileName = getFileName(id, documentType);

        logger.info("Putting object locally");

        String fullPath = dataDir.getPath() + File.separator + fileName;
        File outputFile = createFileHandle(id, documentType);

        try {
            if (!outputFile.exists()) {
                outputFile.createNewFile();
                logger.debug("Made localS3 file: " + fileName + " at folder: " + fullPath);
            }

            Files.copy(createFile(id, documentType, obj), outputFile);

        } catch (IOException e) {
            throw new RuntimeException("Error writing file " + fileName, e);
        }
    }

    @Override
    public <T> T load(Class<T> clazz, String id) {
        return load(clazz, id, clazz.getAnnotation(S3DocumentType.class).value());
    }

    @Override
    public <T> T load(Class<T> clazz, String id, String documentType) {
        File readFile = createFileHandle(id, documentType);
        String fileName = readFile.getAbsolutePath();

        if (readFile.exists()) {
            try (FileInputStream inputStream = new FileInputStream(readFile)) {
                return getFileContent(clazz, inputStream);

            } catch (FileNotFoundException e) {
                throw new RuntimeException("Error finding file " + fileName, e);
            } catch (IOException e) {
                throw new RuntimeException("Error reading content from file " + fileName, e);
            }
        }

        logger.warn("File " + fileName + " does not exist.");
        return null;
    }

    @Override
    public <T> void delete(Class<T> clazz, String id) {
        delete(id, clazz.getAnnotation(S3DocumentType.class).value());
    }

    @Override
    public void delete(String id, String documentType) {
        File file = createFileHandle(id, documentType);
        file.delete();
    }

    private <T> File createFile(String id, String documentType, T objectToPersist) {
        String filename = getFileName(id, documentType);
        try {
            File file = File.createTempFile(filename, DEFAULT_TEMP_FILE_SUFFIX);
            file.deleteOnExit();

            String stringValue = objectMapper.writeValueAsString(objectToPersist);

            try (Writer writer = new OutputStreamWriter(new FileOutputStream(file))) {
                writer.write(stringValue);
                writer.close();
            }

            return file;
        } catch (IOException e) {
            throw new RuntimeException("Error trying to create file for object " + filename, e);
        }
    }

    private <T> T getFileContent(Class<T> clazz, InputStream inputStream) throws IOException {
        return objectMapper.readValue(IOUtils.toString(inputStream), clazz);
    }

    private File createFileHandle(String documentId, String documentType) {
        String fileName = getFileName(documentId, documentType);
        logger.info("Getting object with id " + documentId);

        String fullPath = dataDir.getPath() + File.separator + fileName;
        return new File(fullPath);
    }

    private String getFileName(String documentId, String documentType) {
        return String.format("%s.%s", documentId, documentType);
    }
}
