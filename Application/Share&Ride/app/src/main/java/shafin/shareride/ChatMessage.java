package shafin.shareride;

/**
 * Created by shafins on 10/12/17.
 */

public class ChatMessage {
    String name,message;


    public ChatMessage() {
    }

    public ChatMessage(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public  String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}
