package com.navii.server.persistence.domain;

/**
 * Created by sjung on 10/11/15.
 */
public class UserPreference {
    private String preference;
    private String username;

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
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

        private String preference;
        private String username;


        private Builder() {}

        Builder preference(String preference) {
            this.preference = preference;
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
