package com.navii.server.controller;

import com.navii.server.domain.Attraction;
import com.navii.server.domain.Itinerary;
import com.navii.server.service.AttractionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping(value = "/attraction")
public class AttractionController {
    private static final Logger logger = LoggerFactory.getLogger(AttractionController.class);

    @Autowired
    private AttractionService attractionService;

    @RequestMapping(method=RequestMethod.POST)
    @ResponseBody
    public Attraction createAttraction(@RequestBody Attraction attraction) {
        Attraction created = attractionService.save(attraction);
        return created;
    }
}
