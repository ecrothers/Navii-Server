package com.navii.server.persistence.controller;

import com.navii.server.persistence.domain.Preference;
import com.navii.server.persistence.service.PreferenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sjung on 10/11/15.
 */
@RestController
@RequestMapping(value = "/preference")
public class PreferenceController {
    private static final Logger logger = LoggerFactory.getLogger(PreferenceController.class);

    @Autowired
    private PreferenceService preferenceService;

    @RequestMapping(value="/", method= RequestMethod.POST)
    public Preference createItinerary(@RequestBody Preference preference) {
        Preference created = preferenceService.save(preference);
        return created;
    }
}