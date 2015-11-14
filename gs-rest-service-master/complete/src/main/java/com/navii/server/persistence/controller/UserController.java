package com.navii.server.persistence.controller;

import com.navii.server.persistence.domain.User;
import com.navii.server.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Created by JMtorii on 2015-10-15.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Gets a user by id
     * @param userId    Identifier for user
     * @return          If user is found, return the user object and HTTP status 302; otherwise, 404
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        User foundUser = userService.findOne(userId);

        if (foundUser != null) {
            return new ResponseEntity<User>(foundUser, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Gets all users
     * @return      If users exist, return list of users and HTTP status 302; otherwise, 404
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<User>> getAllUsers() {
        ArrayList<User> users = userService.findAll();

        if (users != null) {
            return new ResponseEntity<ArrayList<User>>(users, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<ArrayList<User>>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Creates a new user if the id and username do not exist
     * @param user  User to persist in server
     * @return      If user is successfully created, return HTTP status 201; otherwise, 400
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.create(user);

        if (createdUser != null) {
            return new ResponseEntity<User>(HttpStatus.CREATED);
        } else {
            // TODO: choose better HTTP status
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Updates an existing user
     * @param userId    Identifier for user
     * @param user      User to persist in server
     * @return          If the user exists and is changed, return HTTP status 202; otherwise 404.
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable String userId, @RequestBody User user) {
        User updatedUser = userService.update(userId, user);

        if (updatedUser != null) {
            return new ResponseEntity<User>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes an existing user
     * @param userId    Identifier for the user
     * @return          If the user exists and is deleted, return HTTP status 202; otherwise 404.
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable String userId) {
        User deletedUser = userService.delete(userId);

        if (deletedUser != null) {
            return new ResponseEntity<User>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }
}
