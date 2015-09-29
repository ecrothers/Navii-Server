package transitobjects;

import navii.AttractionCore;

import java.util.Date;
import java.util.LinkedList;

/**
 * Created by LocalEvan on 2015-07-22.
 */
public class CreateItineraryPayload extends AttractionCore {
    LinkedList<Long> attractionIds;

    public LinkedList<Long> getAttractions() {
        return attractionIds;
    }

    public void setAttractions(LinkedList<Long> aids) {
        attractionIds = aids;
    }

    public Date getEndDate() {
        return endDate;
    }

    public startDate getStartDate() {
        return startDate;
    }
}
