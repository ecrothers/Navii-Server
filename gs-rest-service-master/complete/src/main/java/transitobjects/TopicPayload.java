package transitobjects;

/**
 * Created by LocalEvan on 2015-07-22.
 */
public class TopicPayload {
    private int topicid;
    private int groupid;
    private int createdBy;
    private String title;
    private String description;

    public int getTopicId() {
        return topicid;
    }

    public int getGroupId() {
        return groupid;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
