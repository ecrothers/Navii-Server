package com.navii.server.persistence.domain;

import java.util.List;

/**
 * Created by sjung on 10/11/15.
 */
@SuppressWarnings("unused")
public class UserPreference {
    private List<String> preferences;
    private String username;

    public UserPreference() {}

    private UserPreference(Builder builder) {
        this.username = builder.username;
        this.preferences = builder.preferences;
    }

    public List<String> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<String> preferences) {
        this.preferences = preferences;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {

        private List<String> preferences;
        private String username;

        public Builder() {}

        public Builder preferences(List<String> preferences) {
            this.preferences = preferences;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public UserPreference build() {
            return new UserPreference(this);
        }
    }
}
