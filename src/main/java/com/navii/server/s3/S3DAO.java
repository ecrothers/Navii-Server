package com.navii.server.s3;

/**
 * A simple wrapper around Amazon's S3 offering.
 *
 * Created by JMtorii on 15-10-13.
 */
public interface S3DAO {

    /**
     * Saves the specified object to S3.
     * This implementation saves the object with a compound key that is composed of the id and the value of the
     * {@link S3DocumentType} annotation on class T.
     * For example, if the specified id is {@code 1234} and the value of the {@link S3DocumentType} annotation on T is
     * {@code PlayerData}, then the compound key will be {@code 1234.PlayerData}.
     * @param id the unique id to attach to this object. In general, player id.
     * @param obj the object to save. It must be serializable with Jackson and have an {@link S3DocumentType} annotation.
     * @param <T> the type of object to save. It must be serializable with Jackson and have an {@link S3DocumentType} annotation.
     */
    <T> void save(String id, T obj);

    /**
     * Saves the specified object to S3.
     * This implementation uses the specified id and documentType as a compound key with the format {@code id.documentType}.
     * @param id the unique id to attach to this object. In general, player id.
     * @param documentType the document type of this object. In general, this should represent the type of data that is being saved.
     * @param obj the object to save. It must be serializable with Jackson.
     * @param <T> the type of object to save. It must be serializable with Jackson.
     */
    <T> void save(String id, String documentType, T obj);

    /**
     * Loads the object with the specified id from S3.
     * This implementation loads the object with a compound key that is composed of the id and the value of the
     * {@link S3DocumentType} annotation on class T.
     * For example, if the specified id is {@code 1234} and the value of the {@link S3DocumentType} annotation on T is
     * {@code PlayerData}, then the compound key will be {@code 1234.PlayerData}.
     * @param clazz the type of object referred to by the id. This object must be deserializable with Jackson.
     * @param id the unique id to attach to this object. In general, player id.
     * @param <T> the type of object to save. It must be serializable with Jackson and have an {@link S3DocumentType} annotation.
     * @return the requested object, or null if the object does not exist.
     */
    <T> T load(Class<T> clazz, String id);

    /**
     * Loads the object with the specified id from S3.
     * This implementation uses the specified id and documentType as a compound key with the format {@code id.documentType}.
     * @param clazz the type of object referred to by the id. This object must be deserializable with Jackson.
     * @param id the unique id to attach to this object. In general, player id.
     * @param documentType the document type of this object. In general, this should represent the type of data that is being saved.
     * @param <T> the type of object to save. It must be serializable with Jackson.
     * @return the requested object, or null if the object does not exist.
     */
    <T> T load(Class<T> clazz, String id, String documentType);

    /**
     * Deletes the object with the specified id from S3.
     * This implementation deletes the object with a compound key that is composed of the id and the value of the
     * {@link S3DocumentType} annotation on class T.
     * For example, if the specified id is {@code 1234} and the value of the {@link S3DocumentType} annotation on T is
     * {@code PlayerData}, then the compound key will be {@code 1234.PlayerData}.
     * @param clazz the type of object referred to by the id.
     * @param id the unique id to attach to this object. In general, player id.
     * @param <T> the type of object to save. It must be serializable with Jackson and have an {@link S3DocumentType} annotation.
     */
    <T> void delete(Class<T> clazz, String id);

    /**
     * Deletes the object with the specified id from S3.
     * This implementation uses the specified id and documentType as a compound key with the format {@code id.documentType}.
     * @param id the unique id to attach to this object. In general, player id.
     * @param documentType the document type of this object. In general, this should represent the type of data that is being saved.
     */
    void delete(String id, String documentType);
}
