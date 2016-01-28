package com.navii.server.persistence.controller;

import com.navii.server.persistence.domain.Activity;
import com.navii.server.persistence.domain.Attraction;
import com.navii.server.persistence.domain.Itinerary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/webtool")
public class WebtoolController {
    private static final Logger logger = LoggerFactory.getLogger(WebtoolController.class);

    @RequestMapping(value="/activity", method=RequestMethod.GET)
    public String activityForm(Model model) {
        model.addAttribute("activity", new Activity());
        return "activity/entry";
    }

    @RequestMapping(value="/attraction", method=RequestMethod.GET)
    public String attractionForm(Model model) {
        model.addAttribute("attraction", new Attraction());
        return "attraction/entry";
    }

    /*@RequestMapping(value="/attraction", method=RequestMethod.POST)
    public String attractionSubmit(@ModelAttribute Attraction attraction, Model model) {
        model.addAttribute("attraction", attraction);
        return "attraction/result";
    }*/

    @RequestMapping(value="/itinerary", method=RequestMethod.GET)
    public String itineraryForm(Model model) {
        model.addAttribute("itinerary", new Itinerary());
        return "itinerary/entry";
    }

    /*@RequestMapping(value="/itinerary", method=RequestMethod.POST)
    public String itinerarySubmit(@ModelAttribute Itinerary itinerary, Model model) {
        model.addAttribute("itinerary", itinerary);
        return "itinerary/result";
    }*/
}
