package com.navii.server.persistence.service;

import com.navii.server.persistence.domain.User;

import java.util.List;

/**
 * Created by JMtorii on 2015-10-15.
 */
public interface UserService {

    /**
     * Finds all user entries from the database.
     * @return  All existing users.
     */
    List<User> findAll();

    /**
     * Finds the a single user.
     * @param userId    The userId of the requested User entry.
     * @return          The found user.
     */
    User findOne(int userId);

    /**
     * Creates a new user entry to the database.
     * @param createdUser       The information of the new user entry.
     * @return                  The number of created users.
     */
    int create(User createdUser);

    /**
     * Updates an existing user in the database.
     * @param updatedUser   The information of the updated user entry.
     * @return              The number of updated users.
     */
    int update(User updatedUser);

    /**
     * Deletes a user from the database.
     * @param userId        ID to delete from database.
     * @return              The number of deleted users.
     */
    int delete(int userId);

    /**
     * Deletes all users
     * @return              The number of deleted users.
     */
    int deleteAll();

    /**
     * Attempts to sign up a user
     * @param username      Username of the user.
     * @param password      Password of the user.
     */
    void signUp(String username, String password);
}
