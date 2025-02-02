package bhushan.org.GHRCEUSER.Chating;

public class Message {
    private String senderId;
    private String message;
    private String messageType;
    private Object timestamp;

    // Empty constructor required for Firestore
    public Message() {}

    public Message(String senderId, String message, String messageType, Object timestamp) {
        this.senderId = senderId;
        this.message = message;
        this.messageType = messageType;
        this.timestamp = timestamp;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageType() {
        return messageType;
    }

    public Object getTimestamp() {
        return timestamp;
    }

}
