package com.navii.server.persistence.controller;

import com.navii.server.persistence.domain.UserPreference;
import com.navii.server.persistence.service.UserPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * @param userPreference  UserPreferences to persist in server
     * @return      If user is successfully created, return HTTP status 201; otherwise, 400
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> createUserPreference(@RequestBody UserPreference userPreference) {
        boolean createdUserPreference = userPreferenceService.create(userPreference);

        if (createdUserPreference) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            // TODO: choose better HTTP status
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Deletes all userspreference that are of a given preference Type
     * @param username    Identifier for the user
     * @param preferenceType Identifier for the preferenceType
     * @return          Deletes all UserPreference return HTTP status 202; otherwise 404.
     *
     * TODO: change path variables to request parameters
     */
    @RequestMapping(value = "/{username}/{preferenceType}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAllUserPreference(@PathVariable String username, @PathVariable("preferenceType") int preferenceType) {
        int deletedUsersPreferences = userPreferenceService.deleteAllPreference(username, preferenceType);

        if (deletedUsersPreferences > 0) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
