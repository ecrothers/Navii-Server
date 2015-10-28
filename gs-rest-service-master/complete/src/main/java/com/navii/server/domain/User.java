package com.navii.server.domain;

/**
 * Created by JMtorii on 2015-10-15.
 */
@SuppressWarnings("unused")
public class User {

    private int id;
    private String username;
    private String password;
    private String salt;
    private String isFacebook;
    private String preferences;
    private String tags;

    public User() {}

    private User(Builder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.password = builder.password;
        this.salt = builder.salt;
        this.isFacebook = builder.isFacebook;
    }

    public int getId() {
        return id;
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

    public String isFacebook() {
        return isFacebook;
    }

    public String getPreferences() {
        return tags;
    }

    public String getTags() {
        return preferences;
    }

    static class Builder {
        private int id;
        private String username;
        private String password;
        private String salt;
        private String isFacebook;
        private String tags;
        private String preferences;

        private Builder() {}

        public Builder id(int id) {
            this.id = id;
            return this;
        }

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

        public Builder isFacebook(String isFacebook) {
            this.isFacebook = isFacebook;
            return this;
        }

        Builder preferences(String preferences) {
            this.preferences = preferences;
            return this;
        }

        Builder tags(String tags) {
            this.tags = tags;
            return this;
        }

        User build() {
            return new User(this);
        }
    }
}
