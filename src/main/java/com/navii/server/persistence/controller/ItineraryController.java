package com.navii.server.persistence.controller;

import com.navii.server.persistence.domain.Itinerary;
import com.navii.server.persistence.service.ItineraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/itinerary")
public class ItineraryController {
    private static final Logger logger = LoggerFactory.getLogger(ItineraryController.class);

    @Autowired
    private ItineraryService itineraryService;

    /**
     * Gets a Itinerary by id
     * @param itineraryId   Identifier for Itinerary
     * @return          If Itinerary is found, return the Itinerary object and HTTP status 302; otherwise, 404
     */
    @RequestMapping(value = "/{itineraryId}", method = RequestMethod.GET)
    public ResponseEntity<Itinerary> getItinerary(@PathVariable String itineraryId) {
        Itinerary foundItinerary = itineraryService.findOne(itineraryId);

        if (foundItinerary != null) {
            return new ResponseEntity<>(foundItinerary, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Gets all Itinerarys in database
     * @return      If Geese exist, return list of Geese and HTTP status 302; otherwise, 404
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Itinerary>> getAllItinerarys() {
        List<Itinerary> itin = itineraryService.findAll();

        if (itin != null) {
            return new ResponseEntity<>(itin, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Creates a new Itinerary if the itinerary does not already exist
     * @param itinerary Itinerary to persist in server
     * @return      If Itinerary is successfully created, return HTTP status 201; otherwise, 400
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Itinerary> createItinerary(@RequestBody Itinerary itinerary) {
        int numCreatedItinerary = itineraryService.create(itinerary);

        if (numCreatedItinerary > 0) {
            return new ResponseEntity<>(itinerary, HttpStatus.CREATED);
        } else {
            // TODO: choose better HTTP status
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Updates an existing Itinerary
     * @param itineraryId   Identifier for Itinerary
     * @param itinerary     Itinerary to persist in server
     * @return          If the Itinerary exists and is changed, return HTTP status 202; otherwise 404.
     */
    @RequestMapping(value = "/{itineraryId}", method = RequestMethod.PUT)
    public ResponseEntity<Itinerary> updateItinerary(@PathVariable String itineraryId, @RequestBody Itinerary itinerary) {
        int numUpdatedItinerary = itineraryService.update(itineraryId, itinerary);

        if (numUpdatedItinerary > 0) {
            return new ResponseEntity<>(itinerary, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes an existing Itinerary
     * @param itineraryId   Identifier for the Itinerary
     * @return          If the Itinerary exists and is deleted, return HTTP status 202; otherwise 404.
     */
    @RequestMapping(value = "/{itineraryId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteItinerary(@PathVariable String itineraryId) {
        int numDeletedItinerary = itineraryService.delete(itineraryId);

        if (numDeletedItinerary > 0) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Returns a recommended list of Itineraries based on tag list
     * @param tagList   List of tags
     * @return  List of itineraries
     */
    @RequestMapping(value="/tags/{tag_list}/{num_days}" , method= RequestMethod.GET)
    public ResponseEntity<List<Itinerary>> getItinerariesFromTags(@PathVariable("tag_list") String[] tagList, @PathVariable("num_days") int days) {
        List<String> tags = new ArrayList<>();
        if (tagList != null) {
            tags = Arrays.asList(tagList);
        }
        List<Itinerary> itinerary = itineraryService.getItineraries(tags, days);

        if (itinerary.size() > 0) {
            return new ResponseEntity<>(itinerary, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/tags//{num_days}" , method= RequestMethod.GET)
    public ResponseEntity<List<Itinerary>> getItinerariesFromTags(@PathVariable("num_days") int days) {
        return getItinerariesFromTags(null, days);
    }
}
