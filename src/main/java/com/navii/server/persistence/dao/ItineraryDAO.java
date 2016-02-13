package com.navii.server.persistence.dao;

import com.navii.server.persistence.domain.Itinerary;

import java.util.List;

/**
 * Created by ecrothers on 2015-10-08.
 */
public interface ItineraryDAO {
    /**
     * Deletes a Itinerary entry from the database.
     * @param itineraryId   The id of the itinerary entry to delete
     */
    int delete(final int itineraryId);

    /**
     * Finds all Itinerary entries from the database.
     * @return  The information of all Itinerary entries that are found from the database.
     */
    List<Itinerary> findAll();

    /**
     * Finds the information of a single Itinerary entry.
     * @param itineraryId    The id of the requested Itinerary entry.
     * @return      The information of the found Itinerary entry.
     */
    Itinerary findOne(final int itineraryId);

    /**
     * Saves a new Itinerary entry to the database.
     * @param created   The information of the created Itinerary entry.
     * @return          The number of created Itineraries
     */
    int create(final Itinerary created);

    /**
     * Update an existing Itinerary entry in the database.
     * @param updated   The information of the saved Itinerary entry.
     * @return          The number of updated entries
     */
    int update(final Itinerary updated);

    /**
     * Return list of itineraries based on list of tags
     * @param tagList   The list of tags provided by user
     * @return          The list of itineraries
     */
    List<Itinerary> getItineraries(List<String> tagList);
}
