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
     * Gets a user by id
     * @param userId    Identifier for user
     * @return          If user is found, return the user object and HTTP status 302; otherwise, 400
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable int userId) {
        User foundUser = userService.findOne(userId);

        if (foundUser != null) {
            return new ResponseEntity<>(foundUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Gets all users
     * @return      If users exist, return list of users and HTTP status 302; otherwise, 400
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();

        if (users != null) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Creates a new user if the id and username do not exist
     * @param user  User to persist in server
     * @return      If user is successfully created, return HTTP status 201; otherwise, 400
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User user) {
        int createdUser = userService.create(user);

        if (createdUser > 0) {
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(createdUser, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Updates an existing user
     *
     * NOTE: Currently, there is a foreign key constraint that needs to be modified/removed.
     * @param user      User to persist in server
     * @return          If the user exists and is changed, return HTTP status 202; otherwise 400.
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        int updatedUser = userService.update(user);

        if (updatedUser > 0) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Deletes an existing user
     * @param userId    Identifier for the user
     * @return          If the user exists and is deleted, return HTTP status 202; otherwise 400.
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable int userId) {
        int deletedUser = userService.delete(userId);

        if (deletedUser > 0) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Deletes all users
     * @return          The number of deleted rows with a HTTP status 202; otherwise 400.
     */
    @RequestMapping(value = "/all", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAll() {
        return new ResponseEntity<>(userService.deleteAll(), HttpStatus.ACCEPTED);
    }

    /**
     * This is a crappy implementation of the sign up endpoints. This will most likely be modified or removed
     * in later implementations.
     *
     * @param username Username to add to the user
     * @param password Password attached to the user
     * @return If the username already exists, return a 400. Otherwise, return a 200 to indicate that a user has been
     * created.
     */
    @RequestMapping(value = "/username/{username}/password/{password}/signUp", method = RequestMethod.POST)
    public ResponseEntity<?> signUp(@PathVariable String username, @PathVariable String password) {
        // TODO: validate username and password
        // TODO: implement me
//        try {
//            signUp(username, password);
//        } catch (UserException e) {
//
//        }
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
