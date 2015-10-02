package navii;

import transitobjects.ClientAttractionPayload;
import transitobjects.CreateAttractionPayload;

public class Attraction extends AttractionCore {

    private long attractionId;
    private int price;
    private int duration;
    private String blurbUri;
    private String photoUri;
    private String name;
    private String location;

    public Attraction(CreateAttractionPayload cfp) {
        // TODO: generate unique id with SQL query command?

        this.setAuthor(cfp.getAuthor());
        this.setDescription(cfp.getDescription());
        this.setLatitude(cfp.getLatitude());
        this.setLongitude(cfp.getLongitude());
        this.setName(cfp.getName());
    }

    public ClientAttractionPayload getClientPayload() {
        ClientAttractionPayload c = new ClientAttractionPayload();
        c.setAuthor(this.getAuthor());
        c.setDescription(this.getDescription());
        c.setLatitude(this.getLatitude());
        c.setLongitude(this.getLongitude());
        c.setName(this.getName());

        return c;
    }

    public long getId() {
        return attractionId;
    }

    public void setId(long id) {
        this.attractionId = id;
    }

    public int getPrice() {
        return price;
    }

    public void getPrice(long id) {
        this.attractionId = id;
    }
}
