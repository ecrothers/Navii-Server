package com.navii.server.persistence.domain;

import java.util.ArrayList;

/**
 * Created by sjung on 10/11/15.
 */
public class UserPreference {
    private ArrayList<String> preferences;
    private String username;

    public ArrayList<String> getPreferences() {
        return preferences;
    }

    public void setPreferences(ArrayList<String> preferences) {
        this.preferences = preferences;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserPreference() {}

    public UserPreference(Builder builder) {

    }

    static Builder getBuilder() {
        return new Builder();
    }

    static class Builder {

        private ArrayList<String> preferences;
        private String username;


        private Builder() {}

        Builder preferences(ArrayList<String> preferences) {
            this.preferences = preferences;
            return this;
        }

        Builder username(String username) {
            this.username = username;
            return this;
        }


        // TODO: potentially check for non-null values
        UserPreference build() {
            return new UserPreference(this);
        }
    }
}
