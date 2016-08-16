package com.navii.server.persistence.domain;

import java.util.List;

public class Itinerary {
    private int itineraryId;
    private String description;
    private List<PackageScheduleListItem> packageScheduleListItems;
    private int duration;
    private int price;
    private String authorId;

    public Itinerary() {}

    private Itinerary(Builder builder) {
        this.itineraryId = builder.itineraryId;
        this.price = builder.price;
        this.duration = builder.duration;
        this.description = builder.description;
        this.authorId = builder.authorId;
        this.packageScheduleListItems = builder.packageScheduleListItems;
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

    public String getAuthorId() {
        return authorId;
    }

    public String getDescription() {
        return description;
    }

    public List<PackageScheduleListItem> getPackageScheduleListItems() {
        return packageScheduleListItems;
    }

    public static class Builder {
        private int itineraryId;
        private String description;
        private int duration;
        private List<PackageScheduleListItem> packageScheduleListItems;
        private int price;
        private String authorId;

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

        public Builder authorId(String authorId) {
            this.authorId = authorId;
            return this;
        }

        public Builder price(int price) {
            this.price = price;
            return this;
        }

        public Builder packageScheduleListItems(List<PackageScheduleListItem> packageScheduleListItems) {
            this.packageScheduleListItems = packageScheduleListItems;
            return this;
        }
        public Itinerary build() {
            return new Itinerary(this);
        }
    }
}
