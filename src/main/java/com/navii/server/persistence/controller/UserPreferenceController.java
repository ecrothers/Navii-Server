package com.navii.server.persistence.controller;

import com.navii.server.persistence.domain.Preference;
import com.navii.server.persistence.domain.UserPreference;
import com.navii.server.persistence.service.UserPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Created by sjung on 10/11/15.
 */
@RestController
@RequestMapping(value = "/userpreference")
public class UserPreferenceController {

    @Autowired
    private UserPreferenceService userPreferenceService;
    /**
     * Gets all preferences given a username
     * @param username  Username
     * @return      If users exist, return list of preferences and HTTP status 302; otherwise, 404
     */

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<Preference>> getAllUserPreferences(@PathVariable String username) {
        ArrayList<Preference> usersPreferences = userPreferenceService.obtain(username);

        if (usersPreferences != null) {
            return new ResponseEntity<>(usersPreferences, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Creates a new userspreference mapping
     * @param userPreference  User to persist in server
     * @return      If user is successfully created, return HTTP status 201; otherwise, 400
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<UserPreference> createUserPreference(@RequestBody UserPreference userPreference) {
        UserPreference createdUserPreference = userPreferenceService.save(userPreference);

        if (createdUserPreference != null) {
            return new ResponseEntity<UserPreference>(HttpStatus.CREATED);
        } else {
            // TODO: choose better HTTP status
            return new ResponseEntity<UserPreference>(HttpStatus.BAD_REQUEST);
        }
    }

}
