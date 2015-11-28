package com.navii.server.persistence.controller;

import com.navii.server.persistence.domain.User;
import com.navii.server.persistence.service.UserService;
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

    @Autowired
    private UserService userService;

    /**
     * Gets a user by id
     * @param userId    Identifier for user
     * @return          If user is found, return the user object and HTTP status 302; otherwise, 404
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
     * @return      If users exist, return list of users and HTTP status 302; otherwise, 404
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
    public ResponseEntity<User> createUser(@RequestBody User user) {
        int createdUser = userService.create(user);

        if (createdUser > 0) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Updates an existing user
     *
     * NOTE: Currently, there is a foreign key constraint that needs to be modified/removed.
     * @param user      User to persist in server
     * @return          If the user exists and is changed, return HTTP status 202; otherwise 404.
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@RequestBody User user) {
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
     * @return          If the user exists and is deleted, return HTTP status 202; otherwise 404.
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable int userId) {
        int deletedUser = userService.delete(userId);

        if (deletedUser > 0) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
