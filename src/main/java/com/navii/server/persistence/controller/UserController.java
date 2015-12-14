package com.navii.server.persistence.controller;

import com.navii.server.persistence.domain.User;
import com.navii.server.persistence.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by JMtorii on 2015-10-15.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * Gets a user by username
     * @param username      Identifier for user
     * @return              If user is found, return the user object and HTTP status 200; otherwise, 400
     */
    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable String username) {
        User foundUser = userService.findOne(username);

        if (foundUser != null) {
            return new ResponseEntity<>(foundUser, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Gets all users
     * @return      A 200 with a list of users
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    /**
     * Creates a new user if the id and username do not exist
     * @param user  User to persist in server
     * @return      If user is successfully created, return HTTP status 201; otherwise, 400
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User user) {
        int numCreatedUsers = userService.create(user);

        if (numCreatedUsers > 0) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Updates an existing user
     *
     * NOTE: Currently, there is a foreign key constraint that needs to be modified/removed.
     * @param user      User to persist in server
     * @return          If the user exists and is changed, return HTTP status 200; otherwise 400.
     */

    // TODO: change to use exception
    // TODO: this won't make. Path variables and RequestBody don't match
    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        int updatedUser = userService.update(user);

        if (updatedUser > 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Deletes an existing user
     * @param username      Username for the user
     * @return              If the user exists and is deleted, return HTTP status 200; otherwise 400.
     */
    // TODO: change to use exceptions
    @RequestMapping(value = "/{username}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        int deletedUser = userService.delete(username);

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
        userService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * This is a crappy implementation of the sign up endpoints. This will most likely be modified or removed
     * in later implementations.
     *
     * @param username Username to add to the user
     * @param password Password attached to the user
     * @return If the username already exists, return a 409. Otherwise, return a 200 to indicate that a user has been
     * created.
     */
    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public ResponseEntity<?> signUp(@RequestParam(required = true) String username, @RequestParam(required = true) String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        int numCreatedUsers = userService.signUp(username, password);
        if (numCreatedUsers > 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    /**
     * This is a crappy implementation of the login endpoints. This will most likely be modified or removed
     * in later implementations.
     *
     * @param username Username to add to the user
     * @param password Password attached to the user
     * @return If the username already exists, return a 401. Otherwise, return a 200 to indicate success.
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<?> login(@RequestParam(required = true) String username, @RequestParam(required = true) String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        boolean isValid = userService.login(username, password);
        if (isValid) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
