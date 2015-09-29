package navii;

import transitobjects.ClientAttractionPayload;
import transitobjects.CreateAttractionPayload;
import transitobjects.CreateItineraryPayload;

import java.util.Date;

public class Itinerary {
    private long itineraryId;
    private String description;
    private Date startDate;
    private Date endDate;
    private int price;

    public Itinerary(CreateItineraryPayload iti) {
        this.itineraryId = 0; // TODO: generate new ID
        this.description = iti.getDescription();
        this.startDate = iti.getStartDate();
        this.endDate = iti.getEndDate();
    }

    public long getId() {
        return itineraryId;
    }

    public void setId(long id) {
        this.itineraryId = id;
    }

    public int getPrice() {
        return price;
    }

    public void getPrice(int price) {
        this.price = price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate startDate;
    }
}
