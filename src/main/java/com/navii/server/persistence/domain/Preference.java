package com.navii.server.persistence.domain;

/**
 * Created by sjung on 05/11/15.
 */
public class Preference {

    private String preference;
    private int counter;
    private String photoUrl;

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Preference() {}

    public Preference(Builder builder) {

    }

    static Builder getBuilder() {
        return new Builder();
    }

    static class Builder {

        private String preference;
        private int counter;
        private String photoUrl;

        private Builder() {}

        Builder preference(String preference) {
            this.preference = preference;
            return this;
        }

        Builder counter(int counter) {
            this.counter = counter;
            return this;
        }

        Builder photoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
            return this;
        }


        // TODO: potentially check for non-null values
        Preference build() {
            return new Preference(this);
        }
    }
}
