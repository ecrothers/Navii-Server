package com.navii.server.domain;

/**
 * Created by ecrothers on 2015-10-08.
 */
public class Attraction {
    private int attractionId;
    private String name;
    private String location;
    private String photoUri;
    private String blurbUri;
    private int price; // TODO: Should change the schema to use cents as INT.  double-arithmetic = bad
    private int duration;

    //private String description;

    // TODO: Need to rework schema.  lat/lon is better than a location string
    /*private float latitude;
    private float longitude;*/

    //private int authorId;

    public Attraction() {}

    // TODO: Autogenerate attractionId on the DB side
    private Attraction(Builder builder) {
        /*this.attractionId = builder.attractionId;
        this.name = builder.name;
        this.location = builder.location;
        this.photoUri = builder.photoUri;
        this.blurbUri = builder.blurbUri;
        this.price = builder.price;
        this.duration = builder.duration;*/
    }

    static Builder getBuilder() {
        return new Builder();
    }

    public int getAttractionId() {
        return attractionId;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getBlurbUri() {
        return blurbUri;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public int getPrice() {
        return price;
    }

    public int getDuration() {
        return duration;
    }

    static class Builder {
        private int attractionId;
        private String name;
        private String location;
        private String photoUri;
        private String blurbUri;
        private int price;
        private int duration;

        private Builder() {}

        Builder attractionId(int attractionId) {
            this.attractionId = attractionId;
            return this;
        }

        Builder name(String name) {
            this.name = name;
            return this;
        }

        Builder location(String location) {
            this.location = location;
            return this;
        }

        Builder photoUri(String photoUri) {
            this.photoUri = photoUri;
            return this;
        }

        Builder blurbUri(String blurbUri) {
            this.blurbUri = blurbUri;
            return this;
        }

        Builder price(int price) {
            this.price = price;
            return this;
        }

        Builder duration(int duration) {
            this.duration = duration;
            return this;
        }

        /*Builder authorId(int authorId) {
            this.authorId = authorId;
            return this;
        }*/
        /*Builder description(String description) {
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
        }*/

        // TODO: potentially check for non-null values
        Attraction build() {
            return new Attraction(this);
        }
    }
    /*public String getDescription() {
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
    }*/
}
