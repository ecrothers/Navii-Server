package com.navii.server.persistence.domain;

/**
 * Created by sjung on 05/11/15.
 */
public class Tag {

    private String tag;
    private int counter;

    public Tag() {}

    private Tag(Builder builder) {
        this.tag = builder.tag;
        this.counter = builder.counter;
    }

    public String getTag() {
        return tag;
    }

    public int getCounter() {
        return counter;
    }

    public static class Builder {
        private String tag;
        private int counter;

        public Builder() {}

        public Builder tag(String tag) {
            this.tag = tag;
            return this;
        }

        public Builder counter(int counter) {
            this.counter = counter;
            return this;
        }

        public Tag build() {
            return new Tag(this);
        }
    }
}
