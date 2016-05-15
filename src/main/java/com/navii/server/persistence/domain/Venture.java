package com.navii.server.persistence.domain;

/**
 * Created by williamkim on 2016-04-30.
 */
public class Venture {

    private Type type;
    private String category;
    private String option;

    public enum Type {
        MEAL, ATTRACTION
    }

    public Venture(Type type, String category) {
        this.type = type;
        this.category = category;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
