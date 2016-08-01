package com.navii.server.persistence.dao;

import com.navii.server.persistence.domain.Voyager;

import java.util.List;

/**
 * Created by JMtorii on 2015-10-15.
 */
public interface VoyagerDAO {
    /**
     * Finds all user entries from the database.
     * @return  All existing users.
     */
    List<Voyager> findAll();

    /**
     * Finds the information of a single user entry.
     * @param username      The username of the requested user entry.
     * @return              The information of the found user entry.
     */
    //Voyager findOne(String username);

    /**
     * Finds the a single Voyager.
     * @param email     The email.
     * @return          The found Voyager.
     */
    Voyager findByEmail(String email);

    /**
     * Creates a new user entry to the database.
     * @param createdVoyager       The information of the new user entry.
     * @return                  The number of created users
     */
    int create(Voyager createdVoyager);

    /**
     * Updates the password with a given new password
     * @param email          Email of the user
     * @param password       New password
     * @return              The number of updated users.
     */
    int updatePassword(String email, String password);

    /**
     * Updates an existing user in the database.
     * @param updatedVoyager   The information of the updated user entry.
     * @param email         The new email
     * @return              The number of updated users.
     */
    int update(Voyager updatedVoyager, String email);

    /**
     * Deletes a user from the database.
     * @param username      The deleted username.
     * @return              The number of deleted users.
     */
    int delete(String username);

    /**
     * Deletes a user from the database.
     */
    void deleteAll();

    /**
     * Checks if email exists
     * @param email         Email of the user.
     * @return              Whether a user exists
     */
    boolean userExistsFromEmail(String email);

    /**
     * Checks if email and password math
     * @param email         Email of the user.
     * @param password      Password of the user.
     * @return              Whether username and password match
     */
    boolean emailAndPasswordMatch(String email, String password);
}
