package com.navii.server.persistence.dao;


import com.navii.server.persistence.domain.Attraction;

import java.util.List;

/**
 * Created by ecrothers on 2015-10-08.
 */
public interface AttractionDAO {
    /**
     * Deletes a Attraction entry from the database.
     * @param attractionId   The id of the attraction entry to delete
     */
    int delete(final int attractionId);

    /**
     * Finds all Attraction entries from the database.
     * @return  The information of all Attraction entries that are found from the database.
     */
    List<Attraction> findAll();

    /**
     * Finds the information of a single Attraction entry.
     * @param attractionId    The id of the requested Attraction entry.
     * @return      The information of the found Attraction entry.
     */
    Attraction findOne(final int attractionId);

    /**
     * Saves a new Attraction entry to the database.
     * @param created   The information of the created Attraction entry.
     * @return          The number of created Attractions
     */
    int create(final Attraction created);

    /**
     * Update an existing Attraction entry in the database.
     * @param updated   The information of the saved Attraction entry.
     * @return          The number of updated entries
     */
    int update(final Attraction updated);

    /**
     * Insert attraction into database and return generated id
     * @param create
     * @return
     */
    int createAttraction(Attraction create);
}
