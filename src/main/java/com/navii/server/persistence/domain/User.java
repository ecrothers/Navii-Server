package com.navii.server.persistence.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by JMtorii on 2015-10-15.
 */
public class User {

    @JsonProperty(value = "username")
    private String username;

    @JsonProperty(value = "password")
    private String password;

    @JsonProperty(value = "salt")
    private String salt;

    @JsonProperty(value = "is_facebook")
    private boolean isFacebook;

    public User() {}

    private User(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.salt = builder.salt;
        this.isFacebook = builder.isFacebook;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public boolean isFacebook() {
        return isFacebook;
    }

    public static class Builder {
        private String username;
        private String password;
        private String salt;
        private boolean isFacebook;

        public Builder() {}

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder salt(String salt) {
            this.salt = salt;
            return this;
        }

        public Builder isFacebook(boolean isFacebook) {
            this.isFacebook = isFacebook;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
