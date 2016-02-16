package com.navii.server.persistence.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by ecrothers on 2015-10-08.
 */
public class Activity {
    private int activityId;
    //private LocalDateTime startTime;
    //private LocalDateTime endTime;
    private String startTime;
    private String endTime;
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

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
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
        private String startTime;
        private String endTime;
        private int itineraryId;
        private int attractionId;

        public Builder() {}

        public Builder activityId(int activityId) {
            this.activityId = activityId;
            return this;
        }

        public Builder startTime(String startTime) {
            this.startTime = startTime;
            //LocalDateTime dateTime = LocalDateTime.parse(startTime);
            //this.startTime = dateTime;
            return this;
        }

        public Builder endTime(String endTime) {
            this.endTime = endTime;
            //LocalDateTime dateTime = LocalDateTime.parse(endTime);
            //this.endTime = dateTime;
            return this;
        }

        /*public Builder startTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder endTime(LocalDateTime endTime) {
            this.endTime = endTime;
            return this;
        }*/

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
