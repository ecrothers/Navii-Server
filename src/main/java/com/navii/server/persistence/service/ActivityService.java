package com.navii.server.persistence.service;

import com.navii.server.persistence.domain.Activity;

import java.util.List;

/**
 * Created by ecrothers on 2015-10-08.
 */
public interface ActivityService {
    /**
     * Deletes a Activity entry from the database.
     * @param activityId   The id of the Activity to delete from the database
     * @return  The number of deleted Activitys
     */
    int delete(String activityId);

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
    Activity findOne(String activityId);

    /**
     * Saves a new Activity entry to the database.
     * @param saved     The information of the saved Activity entry.
     * @return          Number of created entries
     */
    int create(Activity saved);

    /**
     * Updates an existing Activity entry in the database.
     * @param activityId       The activityId of the requested Activity entry.
     * @param updatedActivity  The information of the updated Activity entry.
     * @return              The number of updated Activity objects.
     */
    int update(String activityId, Activity updatedActivity);
}
