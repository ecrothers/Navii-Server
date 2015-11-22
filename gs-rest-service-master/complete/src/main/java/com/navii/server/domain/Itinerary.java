package com.navii.server.domain;

import java.sql.Date;

public class Itinerary {
    private int itineraryId;
    private String description;
    private int duration;
    private int price;
    private int authorId;

    public Itinerary() {}

    private Itinerary(Builder builder) {
        this.itineraryId = builder.itineraryId;
        this.price = builder.price;
        this.duration = builder.duration;
        this.description = builder.description;
        this.authorId = builder.authorId;
    }

    public int getItineraryId() {
        return itineraryId;
    }

    public int getPrice() {
        return price;
    }

    public int getDuration() {
        return duration;
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
        private int duration;
        private int price;
        private int authorId;

        private Builder() {}

        Builder itineraryId(int itineraryId) {
            this.itineraryId = itineraryId;
            return this;
        }

        Builder duration(int duration) {
            this.duration = duration;
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

        Itinerary build() {
            return new Itinerary(this);
        }
    }
}
