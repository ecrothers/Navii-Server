package com.navii.server.persistence.domain;

import java.sql.Date;

public class Itinerary {
    // TODO: Should probably have a "name" too
    private int itineraryId;
    private String description;
    private Date startDate;
    private Date endDate;
    private int price;
    private int authorId;

    public Itinerary() {}

    private Itinerary(Builder builder) {
        /*this.itineraryId = builder.itineraryId;
        this.price = builder.price;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.description = builder.description;
        this.authorId = builder.authorId;*/
    }

    public int getItineraryId() {
        return itineraryId;
    }

    public int getPrice() {
        return price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getAuthorId() {
        return authorId;
    }

    public String getDescription() {
        return description;
    }

    static Builder getBuilder() {
        return new Builder();
    }

    static class Builder {
        private int itineraryId;
        private String description;
        private Date startDate;
        private Date endDate;
        private int price;
        private int authorId;

        private Builder() {}

        Builder itineraryId(int itineraryId) {
            this.itineraryId = itineraryId;
            return this;
        }

        Builder startDate(Date startDate) {
            this.startDate = startDate;
            return this;
        }

        Builder endDate(Date endDate) {
            this.endDate = endDate;
            return this;
        }

        Builder description(String description) {
            this.description = description;
            return this;
        }

        Builder authorId(int authorId) {
            this.authorId = authorId;
            return this;
        }

        Builder price(int price) {
            this.price = price;
            return this;
        }


        // TODO: potentially check for non-null values
        Itinerary build() {
            return new Itinerary(this);
        }
    }
}
