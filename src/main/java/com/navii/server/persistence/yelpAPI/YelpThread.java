package com.navii.server.persistence.yelpAPI;

import com.navii.server.persistence.domain.Attraction;
import com.navii.server.persistence.domain.Location;
import com.navii.server.persistence.domain.Venture;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import java.util.*;
import java.util.stream.Collectors;

import static com.navii.server.persistence.yelpAPI.ZomatoAPI.getZomatoPrice;

/**
 * Code sample for accessing the Yelp API V2.
 * <p>
 * This program demonstrates the capability of the Yelp API version 2.0 by using the Search API to
 * query for businesses by a search term and location, and the Business API to query additional
 * information about the top result nfrom the search query.
 * <p>
 * <p>
 * See <a href="http://www.yelp.com/developers/documentation">Yelp Documentation</a> for more info.
 */

public class YelpThread extends Thread {

    private static final String API_HOST = "api.yelp.com";
    private static final String DEFAULT_LOCATION = "Toronto, ON";
    private static final String DEFAULT_RADIUS_FILTER = "2000";
    //TODO: CHANGE TO ACTUAL LIMIT
    private static final int SEARCH_LIMIT = 5;

    private static final String SEARCH_PATH = "/v2/search";

    private static final String CONSUMER_KEY = "bC7-svAVQhRSS5Xt6mWx_w";
    private static final String CONSUMER_SECRET = "xu1yiJin42FEUg8xl4RFviDZqdg";
    private static final String TOKEN = "55bwV2Mxx8ZFvR41cx-pWawFpGLKlRoa";
    private static final String TOKEN_SECRET = "I55U5TwC4APJ-St6_LGDtN_GBFM";

    private static final String REQUEST_TERM = "term";
    private static final String REQUEST_LOCATION = "location";
    private static final String REQUEST_CLL = "cll";
    private static final String REQUEST_LIMIT = "limit";
    private static final String REQUEST_RADIUS_FILTER = "radius_filter";
    private static final String REQUEST_SORT = "sort";
    private static final String REQUEST_CATEGORY_FILTER = "category_filter";

    private final static String JSON_BUSINESSES = "businesses";
    private final static String JSON_LOCATION = "location";
    private final static String JSON_NAME = "name";

    private List<Attraction> attractions;
    private Set<String> uniqueCheckHashSet;
    private List<Venture> potentialAttractionStack;
    private Set<JSONObject> attractionsPrefetch;
    private Set<JSONObject> mealPrefetch;
    private int sort;
    private String TAG;

    private static Map<Integer, String> nameMap = new HashMap<>();

    static {
        nameMap.put(0, "Best Matched");
        nameMap.put(1, "By Distance");
        nameMap.put(2, "Highest Rated");
    }

    public static String getYelpName(int id) {
        return nameMap.get(id);
    }

    OAuthService service;
    Token accessToken;

    public YelpThread(List<Venture> potentialAttractionStack, int sort, String tag) {
        this.service = new ServiceBuilder().provider(TwoStepOAuth.class).apiKey(CONSUMER_KEY)
                .apiSecret(CONSUMER_SECRET).build();
        this.accessToken = new Token(TOKEN, TOKEN_SECRET);
        this.potentialAttractionStack = potentialAttractionStack;
        this.sort = sort;
        this.TAG = tag;
        this.uniqueCheckHashSet = new HashSet<>();
        this.attractionsPrefetch = new HashSet<>();
        this.mealPrefetch = new HashSet<>();
    }

    /**
     * Creates and sends a request to the Search API by venture and location.
     * <p>
     * See <a href="http://www.yelp.com/developers/documentation/v2/search_api">Yelp Search API V2</a>
     * for more info.
     *
     * @param venture <tt>String</tt> of the search venture to be queried
     * @return <tt>String</tt> JSON Response
     */
    public String searchForBusinessesByLocation(Venture venture, String cll, int sort) {
        OAuthRequest request = createOAuthRequest(SEARCH_PATH);
        //TODO: PUT INTO OBJECTS
        request.addQuerystringParameter(REQUEST_TERM, venture.getTerm());
        request.addQuerystringParameter(REQUEST_LOCATION, DEFAULT_LOCATION);
        request.addQuerystringParameter(REQUEST_CLL, cll);
        request.addQuerystringParameter(REQUEST_LIMIT, String.valueOf(SEARCH_LIMIT));
        request.addQuerystringParameter(REQUEST_RADIUS_FILTER, DEFAULT_RADIUS_FILTER);
        request.addQuerystringParameter(REQUEST_SORT, String.valueOf(sort));
        if (!venture.getCategories().isEmpty()) {
            request.addQuerystringParameter(REQUEST_CATEGORY_FILTER, venture.getCategories());
        }
        return sendRequestAndGetResponse(request);
    }


    /**
     * Creates and returns an {@link OAuthRequest} based on the API endpoint specified.
     *
     * @param path API endpoint to be queried
     * @return <tt>OAuthRequest</tt>
     */
    private OAuthRequest createOAuthRequest(String path) {
        OAuthRequest request = new OAuthRequest(Verb.GET, "https://" + API_HOST + path);
        return request;
    }

    /**
     * Sends an {@link OAuthRequest} and returns the {@link Response} body.
     *
     * @param request {@link OAuthRequest} corresponding to the API request
     * @return <tt>String</tt> body of API response
     */
    private String sendRequestAndGetResponse(OAuthRequest request) {
        System.out.println(TAG + ":Querying " + request.getCompleteUrl() + " ...");
        this.service.signRequest(this.accessToken, request);
        Response response = request.send();
        return response.getBody();
    }

    /**
     * Queries the Search API based on the command line arguments and takes the first result to query
     * the Business API.
     *
     * @param venture venture object to define correct attraction
     * @param cll     location of current search place
     * @return an Attraction object
     */
    private Attraction getAttraction(Venture venture, String cll, int sort) {
        String searchResponseJSON =
                searchForBusinessesByLocation(venture, cll, sort);

        JSONParser parser = new JSONParser();
        JSONObject response = null;
        try {
            response = (JSONObject) parser.parse(searchResponseJSON);
        } catch (ParseException pe) {
            System.out.println("Error: could not parse JSON response:");
            System.out.println(searchResponseJSON);
        }

        JSONArray businesses = (JSONArray) response.get(JSON_BUSINESSES);

        if (businesses.size() < 0) {
            return getAttraction(venture, cll, sort);
        }
        Attraction attraction;
        int index = new Random().nextInt(businesses.size());

        JSONObject businessObject = (JSONObject) businesses.remove(index);

        String name = businessObject.getOrDefault(JSON_NAME, "N/A").toString();

        while (uniqueCheckHashSet.contains(name)) {
            index = new Random().nextInt(businesses.size());
            businessObject = (JSONObject) businesses.remove(index);
            name = businessObject.getOrDefault(JSON_NAME, "N/A").toString();
        }
        uniqueCheckHashSet.add(name);

        double latitude = 43.644176, longitude = -79.387375;

        JSONObject location = (JSONObject) businessObject.get(JSON_LOCATION);

        if (location.containsKey("coordinate")) {
            JSONObject coordinate = (JSONObject) location.get("coordinate");
            latitude = (double) coordinate.get("latitude");
            longitude = (double) coordinate.get("longitude");
        }

        Location location1 = new Location.Builder()
                .countryCode(location.getOrDefault("country_code", "N/A").toString())
                .address(location.getOrDefault("display_address", "No Location Found").toString())
                .latitude(latitude)
                .longitude(longitude)
                .city("Toronto")
                .neighborhoods("")
                .build();

        int price = 0;

        if (venture.getType().equals(Venture.Type.MEAL)) {
            JSONArray jsonArray = null;

            if (businessObject.containsKey("categories")) {
                jsonArray = (JSONArray) businessObject.get("categories");
            }

            List<String> categories = new ArrayList<>();
            for (Object object : jsonArray.toArray()) {
                JSONArray array = (JSONArray) object;
                categories.add(array.get(1).toString());
            }
            String zomatoCategories = categories.stream().collect(Collectors.joining(","));

            price = getZomatoPrice(name, zomatoCategories, latitude, longitude);
        }

        String photoUri = businessObject.getOrDefault("image_url", "N/A").toString().replace("ms.jpg", "o.jpg");
        String blurb = businessObject.getOrDefault("snippet_text", "N/A").toString();
        attraction = new Attraction.Builder()
                .name(name)
                .photoUri(photoUri)
                .blurbUri(blurb)
                .location(location1)
                .duration(3)
                .price(price)
                .build();

        for (int i = 0; i < businesses.size(); i++) {
            JSONObject object = (JSONObject) businesses.get(i);
            if (!object.isEmpty()) {
                if (venture.getType() == Venture.Type.ATTRACTION) {
                    attractionsPrefetch.add(object);
                } else {
                    mealPrefetch.add(object);
                }
            }
        }

        return attraction;
    }

    public List<Attraction> buildItinerary(List<Venture> potentialAttractionStack, int sort) {
        //TODO: REPLACE WITH ACTUAL COORDINTATES
        String cll = "43.644176,-79.387375";
        List<Attraction> attractionList = new ArrayList<>();
        for (Venture potentialAttraction : potentialAttractionStack) {
            Attraction attraction = getAttraction(potentialAttraction, cll, sort);
            attractionList.add(attraction);
            double latitude = attraction.getLocation().getLongitude() + 0.00001;
            double longitude = attraction.getLocation().getLongitude() - 0.00001;
            cll = latitude + "," + longitude;
        }

        return attractionList;
    }

    @Override
    public void run() {
        setAttractions(buildItinerary(potentialAttractionStack, sort));
    }

    private void setAttractions(List<Attraction> attractions) {
        this.attractions = attractions;
    }

    public List<Attraction> getAttractions() {
        return attractions;
    }

    public Set<JSONObject> getAttractionsPrefetch() {
        return attractionsPrefetch;
    }

    public Set<JSONObject> getMealPrefetch() {
        return mealPrefetch;
    }
}