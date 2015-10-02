package navii;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import transitobjects.ClientAttractionPayload;
import transitobjects.CreateAttractionPayload;
import transitobjects.CreateItineraryPayload;
import transitobjects.JsonResponse;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping(value = "/navii")
public class AttractionController {
    static final Logger logger = LoggerFactory.getLogger(AttractionController.class);
    private final AtomicLong naviiCounter = new AtomicLong();

    // TODO: More than just one sample navii
    Attraction localTestAttraction;

    @RequestMapping(value="/", method=RequestMethod.POST)
    public String createAttraction(@RequestBody CreateAttractionPayload navii) {
                            /*@RequestParam(value="name") String name,
                             @RequestParam(value="creatorId") long creatorId) {*/
        localTestAttraction = new Attraction(navii);
        long newId = localTestAttraction.getId();

        return "{\"Result\": \"OK\", \"AttractionId\": \"" + newId + "\"}"; // success
    }

    @RequestMapping(value="/", method=RequestMethod.POST)
    public String createItinerary(@RequestBody CreateItineraryPayload itin) {
        Itinerary userItinerary;
        userItinerary = new Itinerary(itin);
        return "{\"Result\": \"OK\", \"Itinerary\": \"" + userItinerary.getId() + "\"}"; // success
    }

    @RequestMapping(value="/", method=RequestMethod.GET)
    public ClientAttractionPayload getAttraction(@RequestParam(value="id") long id) {
        // TODO
        if (localTestAttraction.getId() == id) {
            return localTestAttraction.getClientPayload(); // success
        } else {
            return null; // failure TODO: better error handling
        }
    }
}
