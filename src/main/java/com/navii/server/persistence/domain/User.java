package com.navii.server.persistence.domain;

/**
 * TODO: I'm so sorry for this fucking bullshit. I'll refactor the database soon :(
 *
 * Created by JMtorii on 2015-10-15.
 */
public class User {

    private int userid;
    private String username;
    private String saltedPassword;
    private String salt;
    private String isfacebook;

    public User() {}

    private User(Builder builder) {
        this.userid = builder.userid;
        this.username = builder.username;
        this.saltedPassword = builder.saltedPassword;
        this.salt = builder.salt;
        this.isfacebook = builder.isfacebook;
    }

    public int getId() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public String getSaltedPassword() {
        return saltedPassword;
    }

    public String getSalt() {
        return salt;
    }

    public String isFacebook() {
        return isfacebook;
    }

    public static class Builder {
        private int userid;
        private String username;
        private String saltedPassword;
        private String salt;
        private String isfacebook;

        public Builder() {}

        public Builder id(int id) {
            this.userid = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.saltedPassword = password;
            return this;
        }

        public Builder salt(String salt) {
            this.salt = salt;
            return this;
        }

        public Builder isFacebook(String isFacebook) {
            this.isfacebook = isFacebook;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
