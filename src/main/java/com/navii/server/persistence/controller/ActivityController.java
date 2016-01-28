package com.navii.server.persistence.controller;

import com.navii.server.persistence.domain.Activity;
import com.navii.server.persistence.service.ActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/activity")
public class ActivityController {
    private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);

    @Autowired
    private ActivityService activityService;

    /**
     * Gets a Activity by id
     * @param activityId   Identifier for Activity
     * @return          If Activity is found, return the Activity object and HTTP status 302; otherwise, 404
     */
    @RequestMapping(value = "/{activityId}", method = RequestMethod.GET)
    public ResponseEntity<Activity> getActivity(@PathVariable String activityId) {
        Activity foundActivity = activityService.findOne(activityId);

        if (foundActivity != null) {
            return new ResponseEntity<>(foundActivity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Gets all Activitys in database
     * @return      If Geese exist, return list of Geese and HTTP status 302; otherwise, 404
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Activity>> getAllActivitys() {
        List<Activity> activitys = activityService.findAll();

        if (activitys != null) {
            return new ResponseEntity<>(activitys, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Creates a new Activity if the activity does not already exist
     * @param activity Activity to persist in server
     * @return      If Activity is successfully created, return HTTP status 201; otherwise, 400
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Activity> createActivity(@RequestBody Activity activity) {
        int numCreatedActivity = activityService.create(activity);

        if (numCreatedActivity > 0) {
            return new ResponseEntity<>(activity, HttpStatus.CREATED);
        } else {
            // TODO: choose better HTTP status
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Updates an existing Activity
     * @param activityId   Identifier for Activity
     * @param activity     Activity to persist in server
     * @return          If the Activity exists and is changed, return HTTP status 202; otherwise 404.
     */
    @RequestMapping(value = "/{activityId}", method = RequestMethod.PUT)
    public ResponseEntity<Activity> updateActivity(@PathVariable String activityId, @RequestBody Activity activity) {
        int numUpdatedActivity = activityService.update(activityId, activity);

        if (numUpdatedActivity > 0) {
            return new ResponseEntity<>(activity, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes an existing Activity
     * @param activityId   Identifier for the Activity
     * @return          If the Activity exists and is deleted, return HTTP status 202; otherwise 404.
     */
    @RequestMapping(value = "/{activityId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteActivity(@PathVariable String activityId) {
        int numDeletedActivity = activityService.delete(activityId);

        if (numDeletedActivity > 0) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
