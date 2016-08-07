package com.navii.server.persistence.controller;

import com.navii.server.persistence.domain.Preference;
import com.navii.server.persistence.service.UserPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by sjung on 10/11/15.
 */
@RestController
@RequestMapping(value = "/userpreference")
public class UserPreferenceController {

    @Autowired
    private UserPreferenceService userPreferenceService;

    /**
     * Creates a new userspreferences mapping
     * @param preferences  UserPreferences to persist in server
     * @return      If user is successfully created, return HTTP status 201; otherwise, 400
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> createUserPreference(@RequestBody List<Preference> preferences) {
        boolean createdUserPreference = userPreferenceService.create(preferences);

        if (createdUserPreference) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            // TODO: choose better HTTP status
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Deletes all userspreference that are of a given email
     * @return          Deletes all Preferences return HTTP status 202; otherwise 404.
     *
     * TODO: change path variables to request parameters
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAllUserPreference() {
        int deletedUsersPreferences = userPreferenceService.deleteAllPreference();

        if (deletedUsersPreferences > 0) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
