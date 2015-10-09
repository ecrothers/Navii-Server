package com.navii.server.dao;

import com.navii.server.domain.Attraction;

import java.util.List;

/**
 * Created by ecrothers on 2015-10-08.
 */
public interface AttractionDAO {
    /**
     * Deletes an Attraction entry from the database.
     * @param deleted   The deleted Attraction entry.
     */
    void delete(Attraction deleted);

    /**
     * Finds all Attraction entries from the database.
     * @return  The information of all Attraction entries that are found from the database.
     */
    List<Attraction> findAll();

    /**
     * Finds the information of a single Attraction entry.
     * @param id    The id of the requested Attraction entry.
     * @return      The information of the found Attraction entry.
     */
    Attraction findOne(int id);

    /**
     * Saves a new Attraction entry to the database.
     * @param saved     The information of the saved Attraction entry.
     * @return          The information fo the saved Attraction entry.
     */
    Attraction save(Attraction saved);
}
