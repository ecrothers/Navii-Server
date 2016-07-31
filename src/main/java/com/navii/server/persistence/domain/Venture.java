package com.navii.server.persistence.domain;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by williamkim on 2016-04-30.
 */
public class Venture {

    private Type type;
    private Set<String> categories;
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

    public Venture(Type type) {
        this.type = type;
    }

    public Venture(Type type, String term) {
        this.type = type;
        this.term = term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getTerm() {
        return term;
    }

    public Type getType() {
        return type;
    }

    public String getCategories() {
        if (categories == null || categories.isEmpty()) {
            return "";
        }
        return categories.stream().collect(Collectors.joining(","));
    }

    public void addCategory(String category) {
        if (this.categories == null) {
            this.categories = new HashSet<>();
        }
        categories.add(category);
    }
}
