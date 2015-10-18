package com.navii.server.domain;

/**
 * Created by ecrothers on 2015-10-08.
 */
public class Attraction {
    private String name;
    private String description;

    // TODO: Need to rework schema
    private float latitude;
    private float longitude;
    //

    private int authorId;
    private int attractionId;
    private int price;
    private int duration;
    private String blurbUri;
    private String photoUri;
    private String location;

    private Attraction(Builder builder) {}

    static Builder getBuilder() {
        return new Builder();
    }

    public int getDuration() {
        return duration;
    }

    public String getBlurbUri() {
        return blurbUri;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public String getLocation() {
        return location;
    }

    static class Builder {
        private String name;
        private String description;
        private float latitude;
        private float longitude;
        private int authorId;
        private int attractionId;
        private int price;
        private int duration;
        private String blurbUri;
        private String photoUri;
        private String location;

        private Builder() {}

        Builder price(int price) {
            this.price = price;
            return this;
        }

        Builder duration(int duration) {
            this.duration = duration;
            return this;
        }

        Builder attractionId(int attractionId) {
            this.attractionId = attractionId;
            return this;
        }

        Builder authorId(int authorId) {
            this.authorId = authorId;
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

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public int getAuthorId() {
        return authorId;
    }

    public long getAttractionId() {
        return attractionId;
    }

    public int getPrice() {
        return price;
    }
}
