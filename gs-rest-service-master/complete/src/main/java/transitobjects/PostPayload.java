package transitobjects;

/**
 * Created by LocalEvan on 2015-07-22.
 */
public class PostPayload {
    private int postid;
    private int topicid;
    private int createdby;
    private String text;

    public int getPostId() {
        return postid;
    }

    public int getTopicId() {
        return topicid;
    }

    public int getCreatedByUser() {
        return createdby;
    }

    public String getText() {
        return text;
    }
}
