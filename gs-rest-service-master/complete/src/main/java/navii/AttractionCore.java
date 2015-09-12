package navii;

/**
 * Created by LocalEvan on 2015-07-25.
 */
public abstract class AttractionCore {
    private String name;
    private String description;
    private double latitude;
    private double longitude;
    private long author;
    private int range;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public long getAuthor() {
        return author;
    }

    public int getRange() {
        return range;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public void setLatitude(double lat) {
        this.latitude = lat;
    }

    public void setLongitude(double lo) {
        this.longitude = lo;
    }

    public void setAuthor(long author) {
        this.author = author;
    }

    public void setRange(int range) {
        this.range = range;
    }
}
