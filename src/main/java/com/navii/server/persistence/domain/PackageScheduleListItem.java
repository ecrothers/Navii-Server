package com.navii.server.persistence.domain;

/**
 * Created by sjung on 02/07/16.
 *
 * boolean function forces object to be or not be a header function
 */
public class PackageScheduleListItem {

    private int itemType;
    private String name;
    private Attraction attraction;

    public final static int TYPE_DAY_HEADER = 0;
    public final static int TYPE_MORNING = 1;
    public final static int TYPE_AFTERNOON = 2;
    public final static int TYPE_EVENING = 3;
    public final static int TYPE_ITEM = 4;

    public PackageScheduleListItem() {}

    private PackageScheduleListItem(Builder builder) {
        this.itemType = builder.itemType;
        this.attraction = builder.attraction;
        this.name = builder.name;
    }

    public int getItemType() {
        return itemType;
    }

    public Attraction getAttraction() {
        return attraction;
    }

    public String getName() {
        return name;
    }

    public static class Builder {
        private int itemType;
        private Attraction attraction;
        private String name;

        public Builder itemType(int itemType) {
            this.itemType = itemType;
            return this;
        }

        public Builder attraction(Attraction attraction) {
            this.attraction = attraction;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public PackageScheduleListItem build() {
            return new PackageScheduleListItem(this);
        }
    }
}
