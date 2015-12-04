package com.navii.server.persistence.dao;

import com.navii.server.persistence.domain.User;

import java.util.List;

/**
 * Created by JMtorii on 2015-10-15.
 */
public interface UserDAO {
    /**
     * Finds all user entries from the database.
     * @return  All existing users.
     */
    List<User> findAll();

    /**
     * Finds the information of a single user entry.
     * @param userId    The id of the requested user entry.
     * @return          The information of the found user entry.
     */
    User findOne(int userId);

    /**
     * Creates a new user entry to the database.
     * @param createdUser       The information of the new user entry.
     * @return                  The user id of the created user.
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
     * @param deletedUser   The deleted user entry.
     * @return              The number of deleted users.
     */
    int delete(int deletedUser);

    /**
     * Deletes a user from the database.
     * @return              The number of deleted users.
     */
    int deleteAll();

    /**
     * Checks if username exists
     * @param username      Username of the user.
     * @return              Whether a user exists
     */
    boolean userExistsFromUsername(String username);

    /**
     * Checks if username and password math
     * @param username      Username of the user.
     * @param password      Password of the user.
     * @return              Whether username and password match
     */
    boolean usernameAndPasswordMatch(String username, String password);

    /**
     * Fetches the user id of the user given the username and password
     * @param username      Username of the user.
     * @param password      Password of the user.
     * @return              User id
     */
    int getUserIdFromUsernameAndPassword(String username, String password);
}
