package com.navii.server.persistence.yelpAPI;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;

/**
 * Created by sjung on 28/05/16.
 */
public class ZomatoAPI {

    private static final String ZOMATO_PATH = "https://developers.zomato.com/api/v2.1/";
    private static final String DEFAULT_RADIUS_FILTER = "2000";

    private static String zomatoSearchQuery(String query, String category, double latitude, double longitude) {
        OAuthRequest request = new OAuthRequest(Verb.GET, ZOMATO_PATH + "search");
        //TODO: PUT INTO OBJECTS
        request.addHeader("user-key", "61790317f6b422951550e330a8689edc");
        request.addHeader("Accept", "application/json");
        request.addQuerystringParameter("lat", String.valueOf(latitude));
        request.addQuerystringParameter("lon", String.valueOf(longitude));
        request.addQuerystringParameter("radius", DEFAULT_RADIUS_FILTER);
        request.addQuerystringParameter("sort", "real_distance");
        request.addQuerystringParameter("q", query);
        request.addQuerystringParameter("category", category);

        System.out.println("Querying " + request.getCompleteUrl() + " ...");
        Response response = request.send();
        return response.getBody();
    }

    public static int getZomatoPrice(String name, String category, double latitude, double longitude) {
        String zomatoResponseJSON = zomatoSearchQuery(name, category, latitude, longitude);
        JSONParser parser = new JSONParser();
        JSONObject response = null;

        if (zomatoResponseJSON == null) {
            return -1;
        }
        try {
            response = (JSONObject) parser.parse(zomatoResponseJSON);

        } catch (ParseException e) {
            System.out.println("Error: could not parse JSON response:");
            System.out.println(zomatoResponseJSON);

            e.printStackTrace();
        }
        JSONArray restaurants = (JSONArray) response.get("restaurants");

        //TODO: CHANGE TO SOMETHING BETTER TO MATCH YELP RESTAURANT WITH ZOMATO RESTAURANT (LOCATION, CATEGORIES...)

        JSONObject restaurant = (JSONObject) ((JSONObject) restaurants.get(0)).get("restaurant");

        if (restaurant.containsKey("average_cost_for_two")) {
            return ((Long) restaurant.get("average_cost_for_two")).intValue();
        } else {
            return 0;
        }
    }
}
