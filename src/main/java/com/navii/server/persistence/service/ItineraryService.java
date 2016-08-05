package com.navii.server.persistence.service;

import com.navii.server.persistence.domain.Itinerary;

import java.util.List;

/**
 * Created by ecrothers on 2015-10-08.
 */
public interface ItineraryService {
    /**
     * Deletes a Itinerary entry from the database.
     * @param itineraryId   The id of the Itinerary to delete from the database
     * @return  The number of deleted Itineraries
     */
    int delete(String itineraryId);

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
    Itinerary findOne(String itineraryId);

    /**
     * Saves a new Itinerary entry to the database.
     * @param saved     The information of the saved Itinerary entry.
     * @return          Number of created entries
     */
    int create(Itinerary saved);

    /**
     * Updates an existing Itinerary entry in the database.
     * @param itineraryId       The itineraryId of the requested Itinerary entry.
     * @param updatedItinerary  The information of the updated Itinerary entry.
     * @return              The number of updated Itinerary objects.
     */
    int update(String itineraryId, Itinerary updatedItinerary);

    /**
     * Retrieve a list of itineraries from the tags from list
     * @param tagList List of tags
     * @param days number of days
     * @return          The list of Itineraries
     */
    List<Itinerary> getItineraries(List<String> tagList, int days);
}

