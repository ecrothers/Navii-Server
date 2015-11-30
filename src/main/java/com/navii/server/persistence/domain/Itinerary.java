package com.navii.server.persistence.domain;

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

    public static class Builder {
        private int itineraryId;
        private String description;
        private int duration;
        private int price;
        private int authorId;

        public Builder() {}

        public Builder itineraryId(int itineraryId) {
            this.itineraryId = itineraryId;
            return this;
        }

        public Builder duration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder authorId(int authorId) {
            this.authorId = authorId;
            return this;
        }

        public Builder price(int price) {
            this.price = price;
            return this;
        }

        public Itinerary build() {
            return new Itinerary(this);
        }
    }
}
