package com.navii.server.persistence.domain;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * Created by williamkim on 2016-04-30.
 */
public class Venture {

    private Type type;
    private List<String> categories;
    private String term;

    public enum Type {
        MEAL, ATTRACTION;

        @Override
        public String toString() {
            if (this.equals(MEAL)) {
                return "meal";
            } else {
                return "attraction";
            }
        }
    }

    public Venture(Type type, String term, List<String> categories) {
        this.type = type;
        this.term = term;
        this.categories = categories;
    }

    public Venture(Type type, String term, String category) {
        this.type = type;
        this.categories = Arrays.asList(category);
    }

    public Venture(Type type, String term) {
        this.type = type;
        this.term = term;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getCategories() {
        if (categories == null || categories.isEmpty()) {
            return "";
        }
        return categories.stream().collect(Collectors.joining(","));
    }
}
