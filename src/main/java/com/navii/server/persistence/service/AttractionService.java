package com.navii.server.persistence.service;

import com.navii.server.persistence.domain.Attraction;

import java.util.List;

/**
 * Created by ecrothers on 2015-10-08.
 */
public interface AttractionService {
    /**
     * Deletes a Attraction entry from the database.
     * @param attractionId   The id of the Attraction to delete from the database
     * @return  The number of deleted Attractions
     */
    int delete(String attractionId);

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
    Attraction findOne(String attractionId);

    /**
     * Saves a new Attraction entry to the database.
     * @param saved     The information of the saved Attraction entry.
     * @return          Number of created entries
     */
    int create(Attraction saved);

    /**
     * Updates an existing Attraction entry in the database.
     * @param attractionId       The attractionId of the requested Attraction entry.
     * @param updatedAttraction  The information of the updated Attraction entry.
     * @return              The number of updated Attraction objects.
     */
    int update(String attractionId, Attraction updatedAttraction);
}
