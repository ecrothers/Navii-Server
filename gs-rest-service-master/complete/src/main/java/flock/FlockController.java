package navii;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import transitobjects.ClientNaviiPayload;
import transitobjects.CreateNaviiPayload;
import transitobjects.JsonResponse;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping(value = "/navii")
public class NaviiController {
    static final Logger logger = LoggerFactory.getLogger(NaviiController.class);

    private final AtomicLong naviiCounter = new AtomicLong();

    // TODO: More than just one sample navii
    Navii localTestNavii;

    @RequestMapping(value="/", method=RequestMethod.POST)
    public String createNavii(@RequestBody CreateNaviiPayload navii) {
                            /*@RequestParam(value="name") String name,
                             @RequestParam(value="creatorId") long creatorId) {*/
        localTestNavii = new Navii(navii);
        long newId = localTestNavii.getId();

        return "{\"Result\": \"OK\", \"NaviiId\": \"" + newId + "\"}"; // success
    }

    @RequestMapping(value="/", method=RequestMethod.GET)
    public ClientNaviiPayload getNavii(@RequestParam(value="id") long id) {
        // TODO
        if (localTestNavii.getId() == id) {
            return localTestNavii.getClientPayload(); // success
        } else {
            return null; // failure TODO: better error handling
        }
    }
}
