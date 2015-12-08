package com.navii.server.persistence.controller;

import com.navii.server.persistence.domain.Attraction;
import com.navii.server.persistence.service.AttractionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/attraction")
public class AttractionController {
    private static final Logger logger = LoggerFactory.getLogger(AttractionController.class);

    @Autowired
    private AttractionService attractionService;

    /**
     * Gets a Attraction by id
     * @param attractionId   Identifier for Attraction
     * @return          If Attraction is found, return the Attraction object and HTTP status 302; otherwise, 404
     */
    @RequestMapping(value = "/{attractionId}", method = RequestMethod.GET)
    public ResponseEntity<Attraction> getAttraction(@PathVariable String attractionId) {
        Attraction foundAttraction = attractionService.findOne(attractionId);

        if (foundAttraction != null) {
            return new ResponseEntity<>(foundAttraction, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Gets all Attractions in database
     * @return      If Geese exist, return list of Geese and HTTP status 302; otherwise, 404
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Attraction>> getAllAttractions() {
        List<Attraction> attractions = attractionService.findAll();

        if (attractions != null) {
            return new ResponseEntity<>(attractions, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Creates a new Attraction if the attraction does not already exist
     * @param attraction Attraction to persist in server
     * @return      If Attraction is successfully created, return HTTP status 201; otherwise, 400
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Attraction> createAttraction(@RequestBody Attraction attraction) {
        int numCreatedAttraction = attractionService.create(attraction);

        if (numCreatedAttraction > 0) {
            return new ResponseEntity<>(attraction, HttpStatus.CREATED);
        } else {
            // TODO: choose better HTTP status
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Updates an existing Attraction
     * @param attractionId   Identifier for Attraction
     * @param attraction     Attraction to persist in server
     * @return          If the Attraction exists and is changed, return HTTP status 202; otherwise 404.
     */
    @RequestMapping(value = "/{attractionId}", method = RequestMethod.PUT)
    public ResponseEntity<Attraction> updateAttraction(@PathVariable String attractionId, @RequestBody Attraction attraction) {
        int numUpdatedAttraction = attractionService.update(attractionId, attraction);

        if (numUpdatedAttraction > 0) {
            return new ResponseEntity<>(attraction, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes an existing Attraction
     * @param attractionId   Identifier for the Attraction
     * @return          If the Attraction exists and is deleted, return HTTP status 202; otherwise 404.
     */
    @RequestMapping(value = "/{attractionId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteAttraction(@PathVariable String attractionId) {
        int numDeletedAttraction = attractionService.delete(attractionId);

        if (numDeletedAttraction > 0) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
