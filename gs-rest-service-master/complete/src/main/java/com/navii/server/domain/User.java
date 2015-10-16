package com.navii.server.domain;

/**
 * Created by JMtorii on 2015-10-15.
 */
public class User {

    private int id;
    private String email;
    private String password;
    private String salt;
    private Boolean isFacebook;

    private User(Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.password = builder.password;
        this.salt = builder.salt;
        this.isFacebook = builder.isFacebook;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public Boolean isFacebook() {
        return isFacebook;
    }

    static class Builder {
        private int id;
        private String email;
        private String password;
        private String salt;
        private Boolean isFacebook;

        private Builder() {}

        Builder id(int id) {
            this.id = id;
            return this;
        }

        Builder email(String email) {
            this.email = email;
            return this;
        }

        Builder password(String password) {
            this.password = password;
            return this;
        }

        Builder salt(String salt) {
            this.salt = salt;
            return this;
        }

        Builder isFacebook(Boolean isFacebook) {
            this.isFacebook = isFacebook;
            return this;
        }

        User build() {
            return new User(this);
        }
    }
}
