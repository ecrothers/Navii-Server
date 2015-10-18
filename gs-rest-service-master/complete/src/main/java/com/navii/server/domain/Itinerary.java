package com.navii.server.domain;

import java.sql.Date;

public class Itinerary {
    private long itineraryId;
    private String description;
    private String tags;
    private Date startDate;
    private Date endDate;
    private int price;
    private int authorId;

    private Itinerary(Builder builder) {}

    static Builder getBuilder() {
        return new Builder();
    }

    static class Builder {
        private long itineraryId;
        private String description;
        private String tags;
        private Date startDate;
        private Date endDate;
        private int price;
        private int authorId;

        private Builder() {}

        Builder price(int price) {
            this.price = price;
            return this;
        }

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

        Builder tags(String tags) {
            this.tags = tags;
            return this;
        }

        // TODO: potentially check for non-null values
        Itinerary build() {
            return new Itinerary(this);
        }
    }

    public long getItineraryId() {
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

    public String getTags() {
        return tags;
    }

    public int getAuthorId() {
        return authorId;
    }

    public String getDescription() {
        return description;
    }
}
