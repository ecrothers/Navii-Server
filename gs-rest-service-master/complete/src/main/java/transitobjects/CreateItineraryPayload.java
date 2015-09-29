package transitobjects;

import navii.AttractionCore;

import java.util.Date;
import java.util.LinkedList;

/**
 * Created by LocalEvan on 2015-07-22.
 */
public class CreateItineraryPayload extends AttractionCore {
    private LinkedList<Long> attractionIds;
    private Date startDate;
    private Date endDate;
    private int price;

    public LinkedList<Long> getAttractions() {
        return attractionIds;
    }

    public void setAttractions(LinkedList<Long> aids) {
        attractionIds = aids;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public int getPrice() {
        return price;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
