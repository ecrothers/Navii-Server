package com.navii.server.persistence.controller;

import com.navii.server.persistence.domain.Preference;
import com.navii.server.persistence.service.PreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by sjung on 08/12/15.
 */

@RestController
@RequestMapping(value = "/preference")
public class PreferenceController {

    @Autowired
    private PreferenceService preferenceService;

    /**
     * Fetches a list of preferences based on the preference type specified
     * @param preferenceType The type of preference
     * @return list of preferences
     */
    @RequestMapping(value = "/{preferenceType}", method = RequestMethod.GET)
    public ResponseEntity<List<Preference>> getPreferences(@PathVariable int preferenceType) {
        List<Preference> foundPreferences = preferenceService.getPreferences(preferenceType);
        if (foundPreferences != null) {
            return new ResponseEntity<>(foundPreferences, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
