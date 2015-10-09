package com.navii.server.domain;

/**
 * Created by ecrothers on 2015-10-08.
 */
public class Attraction {
    private String name;
    private String description;
    private double latitude;
    private double longitude;
    private long author;
    private long attractionId;
    private int price;
    private int duration;
    private String blurbUri;
    private String photoUri;
    private String location;

    private Attraction(Builder builder) {}

    static Builder getBuilder() {
        return new Builder();
    }

    static class Builder {
        private int id;
        private int authorid;
        private String name;
        private String description;
        private float latitude;
        private float longitude;
        private double range;

        private Builder() {}

        Builder id(int id) {
            this.id = id;
            return this;
        }

        Builder authorid(int authorid) {
            this.authorid = authorid;
            return this;
        }

        Builder name(String name) {
            this.name = name;
            return this;
        }

        Builder description(String description) {
            this.description = description;
            return this;
        }

        Builder latitude(float latitude) {
            this.latitude = latitude;
            return this;
        }

        Builder longitude(float longitude) {
            this.longitude = longitude;
            return this;
        }

        Builder range(double range) {
            this.range = range;
            return this;
        }

        // TODO: potentially check for non-null values
        Attraction build() {
            return new Attraction(this);
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public long getAuthor() {
        return author;
    }

    public long getId() {
        return attractionId;
    }

    public int getPrice() {
        return price;
    }

    public void getPrice(long id) {
        this.attractionId = id;
    }
}
