package com.navii.server.service;

import com.navii.server.domain.User;

import java.util.ArrayList;

/**
 * Created by JMtorii on 2015-10-15.
 */
public interface UserService {

    /**
     * Finds all User entries from the database.
     * @return  All existing Users.
     */
    ArrayList<User> findAll();

    /**
     * Finds the a single User.
     * @param userId    The userId of the requested User entry.
     * @return          The found user.
     */
    User findOne(String userId);

    /**
     * Creates a new User entry to the database.
     * @param createdUser       The information of the new User entry.
     * @return                  The created user.
     */
    User create(User createdUser);

    /**
     * Updates an existing user in the database.
     * @param userId        The userId of the requested Goose entry.
     * @param updatedUser   The information of the updated User entry.
     * @return              The updated user.
     */
    User update(String userId, User updatedUser);

    /**
     * Deletes a User from the database.
     * @param userId   ID to delete from database.
     */
    User delete(String userId);
}
