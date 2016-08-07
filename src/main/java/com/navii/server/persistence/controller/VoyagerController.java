package com.navii.server.persistence.controller;

import com.navii.server.persistence.domain.Voyager;
import com.navii.server.persistence.domain.VoyagerResponse;
import com.navii.server.persistence.service.VoyagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by JMtorii on 2015-10-15.
 */
@RestController
@RequestMapping(value = "/user")
public class VoyagerController {
    private static final Logger logger = LoggerFactory.getLogger(VoyagerController.class);

    @Autowired
    @Qualifier("voyagerServiceImpl")
    private VoyagerService voyagerService;

    /**
     * Gets a user by email
     * @param email         Identifier for user
     * @return              If user is found, return the user object and HTTP status 200; otherwise, 400
     */
    @RequestMapping(value = "/{email}", method = RequestMethod.GET)
    public ResponseEntity<Voyager> getUser(@PathVariable String email) {
        Voyager foundVoyager = voyagerService.findByEmail(email);

        if (foundVoyager != null) {
            return new ResponseEntity<>(foundVoyager, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Gets all users
     * @return      A 200 with a list of users
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Voyager>> getAllUsers() {
        return new ResponseEntity<>(voyagerService.findAll(), HttpStatus.OK);
    }

    /**
     * Creates a new voyager if the email does not exist
     * @param voyager  Voyager to persist in server
     * @return      If voyager is successfully created, return HTTP status 201; otherwise, 400
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<VoyagerResponse> createUser(@RequestBody Voyager voyager) {
        VoyagerResponse response = voyagerService.create(voyager);

        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Modify password
     *
     * @param email             Email of the user
     * @param oldPassword       Old password
     * @param newPassword       New password
     * @return          If the user exists and is modified, return HTTP status 200; otherwise 400.
     */
    @RequestMapping(value = "/changePassword", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePassword(@RequestParam String email,
                                            @RequestParam String oldPassword,
                                            @RequestParam String newPassword) {
        int updatedUser = voyagerService.updatePassword(email, oldPassword, newPassword);

        if (updatedUser > 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Updates an existing voyager
     *
     * NOTE: Currently, there is a foreign key constraint that needs to be modified/removed.
     * @param voyager      Voyager to persist in server
     * @return          If the voyager exists and is changed, return HTTP status 200; otherwise 400.
     */
    // TODO: change to use exception
    // TODO: this won't make. Path variables and RequestBody don't match
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@RequestBody Voyager voyager, @RequestParam(required = true) String newUsername) {

        int updatedUser = voyagerService.update(voyager,newUsername);

        if (updatedUser > 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Deletes an existing user
     * @param email         Email for the user
     * @return              If the user exists and is deleted, return HTTP status 200; otherwise 400.
     */
    // TODO: change to use exceptions
    @RequestMapping(value = "/{email}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable String email) {
        int deletedUser = voyagerService.delete(email);

        if (deletedUser > 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Deletes all users
     * @return          HTTP status 200
     */
    @RequestMapping(value = "/all", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAll() {
        voyagerService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * TODO: Improve validation and overall handling
     *
     * @param email Username to add to the user
     * @param password Password attached to the user
     * @return If the username already exists, return a 409. Otherwise, return a 200 to indicate that a user has been
     * created.
     */
    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public ResponseEntity<?> signUp(@RequestParam(required = true) String email, @RequestParam(required = true) String username, @RequestParam(required = true) String password) {
        if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        int numCreatedUsers = voyagerService.signUp(email, username, password);
        if (numCreatedUsers > 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    /**
     * This is a crappy implementation of the login endpoints. This will most likely be modified or removed
     * in later implementations.
     *
     * @param email Username to add to the user
     * @param password Password attached to the user
     * @return If the username already exists, return a 401. Otherwise, return a 200 to indicate success.
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<?> login(@RequestParam(required = true) String email, @RequestParam(required = true) String password) {
        if (email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        boolean isValid = voyagerService.login(email, password);
        if (isValid) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
