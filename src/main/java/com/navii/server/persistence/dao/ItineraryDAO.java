package com.navii.server.persistence.dao;

import com.navii.server.persistence.domain.Itinerary;
import com.navii.server.persistence.domain.Tag;

import java.util.List;

/**
 * Created by ecrothers on 2015-10-08.
 */
public interface ItineraryDAO {
    /**
     * Deletes an Itinerary entry from the database.
     * @param deleted   The deleted Itinerary entry.
     */
    void delete(Itinerary deleted);

    /**
     * Finds all Itinerary entries from the database.
     * @return  The information of all Itinerary entries that are found from the database.
     */
    List<Itinerary> findAll();

    /**
     * Finds the information of a single Itinerary entry.
     * @param id    The id of the requested Itinerary entry.
     * @return      The information of the found Itinerary entry.
     */
    Itinerary findOne(int id);

    /**
     * Saves a new Itinerary entry to the database.
     * @param saved     The information of the saved Itinerary entry.
     * @return          The information fo the saved Itinerary entry.
     */
    Itinerary save(Itinerary saved);

    List<Itinerary> getItineraries(List<Tag> tagList);
}
