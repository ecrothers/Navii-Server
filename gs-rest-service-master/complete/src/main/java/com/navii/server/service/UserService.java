package com.navii.server.service;

import com.navii.server.domain.User;

import java.util.List;

/**
 * Created by JMtorii on 2015-10-15.
 */
public interface UserService {

    /**
     * Finds all User entries from the database.
     * @return  The information of all Flock entries that are found from the database.
     */
    List<User> findAll();

    /**
     * Finds the information of a single User entry.
     * @param id    The id of the requested User entry.
     * @return      The information of the found User entry.
     */
    User findOne(int id);

    /**
     * Saves a new User entry to the database.
     * @param savedUser     The information of the saved User entry.
     * @return              The number of affected rows
     */
    User save(User savedUser);

    /**
     * Deletes a User entry from the database.
     * @param deletedUser   The deleted User entry.
     */
    void delete(User deletedUser);
}
