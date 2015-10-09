package com.navii.server.domain;

import java.util.Date;

public class Itinerary {
    private long itineraryId;
    private String description;
    private Date startDate;
    private Date endDate;
    private int price;

    /*public Itinerary(CreateItineraryPayload iti) {
        this.itineraryId = 0; // TODO: generate new ID
        this.description = iti.getDescription();
        this.startDate = iti.getStartDate();
        this.endDate = iti.getEndDate();
        this.price = iti.getPrice();
    }*/

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
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
