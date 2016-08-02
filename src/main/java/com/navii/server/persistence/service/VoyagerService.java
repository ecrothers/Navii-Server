package com.navii.server.persistence.service;

import com.navii.server.persistence.domain.Voyager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * Created by JMtorii on 2015-10-15.
 */
public interface VoyagerService extends UserDetailsService {

    /**
     * Finds all user entries from the database.
     * @return  All existing users.
     */
    List<Voyager> findAll();

    /**
     * Finds the a single Voyager.
     * @param email     The email.
     * @return          The found Voyager.
     */
    Voyager findByEmail(String email);

    /**
     * Loads user based on username
     * @param username Voyager's username
     * @return The found Voyager
     * @throws UsernameNotFoundException
     */
    @Override
    Voyager loadUserByUsername(String username) throws UsernameNotFoundException;

    /**
     * Creates a new user entry to the database.
     * @param createdVoyager       The information of the new user entry.
     * @return                  The number of created users.
     */
    int create(Voyager createdVoyager);

    /**
     * Updates the password with a given new password
     * @param username          Username of the user
     * @param oldPassword       Old password
     * @param newPassword       New password
     * @return              The number of updated users.
     */
    int updatePassword(String username, String oldPassword, String newPassword);

    /**
     * Updates an existing user in the database.
     * @param updatedVoyager   The information of the updated user entry.
     * @return              The number of updated users.
     */
    int update(Voyager updatedVoyager, String username);

    /**
     * Deletes a user from the database.
     * @param username      Username of the user to delete from database.
     * @return              The number of deleted users.
     */
    int delete(String username);

    /**
     * Deletes all users
     */
    void deleteAll();

    /**
     * Attempts to sign up a user
     * @param email         Email of the user.
     * @param username      Username of the user.
     * @param password      Password of the user.
     * @return              The number of created users.
     */
    int signUp(String email, String username, String password);

    /**
     * Attempts to login a user
     * @param email         Email of the user.
     * @param password      Password of the user.
     * @return              Whether the username and password match in the database
     */
    boolean login(String email, String password);

    /**
     * Gets the currently authenticated user.
     * @return      The user object of the currently authenticated user from auth token
     */
    Voyager whoAmI();
}
