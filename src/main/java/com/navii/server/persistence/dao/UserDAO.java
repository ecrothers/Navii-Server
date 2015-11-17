package com.navii.server.persistence.dao;

import com.navii.server.persistence.domain.User;

import java.util.List;

/**
 * Created by JMtorii on 2015-10-15.
 */
public interface UserDAO {
    /**
     * Finds all User entries from the database.
     * @return  All existing Users.
     */
    List<User> findAll();

    /**
     * Finds the information of a single User entry.
     * @param userId    The id of the requested User entry.
     * @return          The information of the found User entry.
     */
    User findOne(int userId);

    /**
     * Creates a new User entry to the database.
     * @param createdUser       The information of the new User entry.
     * @return                  The number of created users.
     */
    int create(User createdUser);

    /**
     * Updates an existing user in the database.
     * @param updatedUser   The information of the updated User entry.
     * @return              The number of updated users.
     */
    int update(User updatedUser);

    /**
     * Deletes a User from the database.
     * @param deletedUser   The deleted User entry.
     * @return              The number of updated users.
     */
    int delete(int deletedUser);
}
