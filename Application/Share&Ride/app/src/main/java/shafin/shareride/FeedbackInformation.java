package shafin.shareride;

/**
 * Created by shafins on 7/12/17.
 */

public class FeedbackInformation {
    String name;
    String id;
    String feedback;


    public FeedbackInformation(String name, String id, String feedback) {
        this.name = name;
        this.id = id;
        this.feedback = feedback;
    }

    public FeedbackInformation() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
