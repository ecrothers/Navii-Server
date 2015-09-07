package navii;

import transitobjects.ClientNaviiPayload;
import transitobjects.CreateNaviiPayload;

import java.util.LinkedList;
import java.util.List;

public class Navii extends NaviiCore {

    private long naviiId;
    //private final List<Long> userIds; if we use nosql, use this

    public Navii(CreateNaviiPayload cfp) {
        // TODO: generate navii id with atomic counter?
        naviiId = 1;

        this.setAuthor(cfp.getAuthor());
        this.setDescription(cfp.getDescription());
        this.setLatitude(cfp.getLatitude());
        this.setLongitude(cfp.getLongitude());
        this.setName(cfp.getName());
        this.setRange(cfp.getRange());
    }

    public ClientNaviiPayload getClientPayload() {
        ClientNaviiPayload c = new ClientNaviiPayload();
        c.setAuthor(this.getAuthor());
        c.setDescription(this.getDescription());
        c.setLatitude(this.getLatitude());
        c.setLongitude(this.getLongitude());
        c.setName(this.getName());
        c.setRange(this.getRange());

        return c;
    }

    public long getId() {
        return naviiId;
    }

    public void setId(long id) {
        this.naviiId = id;
    }

    /*public List<Long> getUserIds() {
        return userIds;
    }

    public void addUser(long userId) {
        userIds.add(userId);
    }*/
}
