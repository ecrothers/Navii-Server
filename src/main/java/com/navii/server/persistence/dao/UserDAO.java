package com.navii.server.persistence.dao;

import com.navii.server.persistence.domain.User;

import java.util.ArrayList;

/**
 * Created by JMtorii on 2015-10-15.
 */
public interface UserDAO {
    /**
     * Finds all User entries from the database.
     * @return  All existing Users.
     */
    ArrayList<User> findAll();

    /**
     * Finds the information of a single User entry.
     * @param username   The username of the requested User entry.
     * @return           The information of the found User entry.
     */
    User findOne(String username);

    /**
     * Finds the information of a single User entry.
     * @param userId    The id of the requested User entry.
     * @return          The information of the found User entry.
     */
    User findOne(final int userId);

    /**
     * Creates a new User entry to the database.
     * @param createdUser       The information of the new User entry.
     * @return                  The created user.
     */
    User create(final User createdUser);

    /**
     * Updates an existing user in the database.
     * @param updatedUser   The information of the updated User entry.
     * @return              The updated user.
     */
    User update(final User updatedUser);

    /**
     * Deletes a User from the database.
     * @param deletedUser   The deleted User entry.
     */
    User delete(final int deletedUser);
}
