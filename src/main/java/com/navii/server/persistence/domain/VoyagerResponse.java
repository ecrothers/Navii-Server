package com.navii.server.persistence.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by JMtorii on 2016-08-06.
 */
public class VoyagerResponse {
    @JsonProperty(value = "voyager")
    private Voyager voyager;

    @JsonProperty(value = "token")
    private String token;

    public VoyagerResponse() {}

    private VoyagerResponse(Builder builder) {
        this.voyager = builder.voyager;
        this.token = builder.token;
    }

    public Voyager getVoyager() {
        return voyager;
    }

    public String getToken() {
        return token;
    }

    public static class Builder {
        private Voyager voyager;
        private String token;

        public Builder() {}

        public Builder voyager(Voyager voyager) {
            this.voyager = voyager;
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public VoyagerResponse build() {
            return new VoyagerResponse(this);
        }
    }
}
