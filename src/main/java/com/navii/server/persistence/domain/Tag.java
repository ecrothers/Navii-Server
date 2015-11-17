package com.navii.server.persistence.domain;

/**
 * Created by sjung on 05/11/15.
 */
public class Tag {

    private String tag;
    private int counter;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public Tag() {}

    private Tag(Builder builder) {

    }

    public static Builder getBuilder() {
        return new Builder();
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

        // TODO: potentially check for non-null values
        public Tag build() {
            return new Tag(this);
        }
    }
}
