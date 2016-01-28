package com.navii.server.persistence.dao;


import com.navii.server.persistence.domain.Activity;

import java.util.List;

/**
 * Created by ecrothers on 2015-10-08.
 */
public interface ActivityDAO {
    /**
     * Deletes a Activity entry from the database.
     * @param activityId   The id of the activity entry to delete
     */
    int delete(final int activityId);

    /**
     * Finds all Activity entries from the database.
     * @return  The information of all Activity entries that are found from the database.
     */
    List<Activity> findAll();

    /**
     * Finds the information of a single Activity entry.
     * @param activityId    The id of the requested Activity entry.
     * @return      The information of the found Activity entry.
     */
    Activity findOne(final int activityId);

    /**
     * Saves a new Activity entry to the database.
     * @param created   The information of the created Activity entry.
     * @return          The number of created Activitys
     */
    int create(final Activity created);

    /**
     * Update an existing Activity entry in the database.
     * @param updated   The information of the saved Activity entry.
     * @return          The number of updated entries
     */
    int update(final Activity updated);
}
