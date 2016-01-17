package com.navii.server.persistence.domain;

import java.time.LocalDateTime;

/**
 * Created by ecrothers on 2015-10-08.
 */
public class Activity {
    private int activityId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int itineraryId;
    private int attractionId;

    public Activity() {}

    private Activity(Builder builder) {
        this.activityId = builder.activityId;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
        this.itineraryId = builder.itineraryId;
        this.attractionId = builder.attractionId;
    }

    public int getActivityId() {
        return activityId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public int getItineraryId() {
        return itineraryId;
    }

    public int getAttractionId() {
        return attractionId;
    }

    public static class Builder {
        private int activityId;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private int itineraryId;
        private int attractionId;

        public Builder() {}

        public Builder activityId(int activityId) {
            this.activityId = activityId;
            return this;
        }

        public Builder startTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder endTime(LocalDateTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public Builder  itineraryId(int itineraryId) {
            this.itineraryId = itineraryId;
            return this;
        }

        public Builder attractionId(int attractionId) {
            this.attractionId = attractionId;
            return this;
        }

        public Activity build() {
            return new Activity(this);
        }
    }
}
