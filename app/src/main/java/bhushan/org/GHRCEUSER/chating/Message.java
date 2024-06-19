package bhushan.org.GHRCEUSER.chating;

public class Message {

    private String senderId;
    private String senderName; // Add sender's name
    private String senderEmail; // Add sender's email
    // Add other profile data as needed
    private String messageText;
    private String attachmentUrl; // URL for image, video, or document
    private String attachmentType; // Type of attachment: "image", "video", "document"
    private long timestamp;

    public Message() {
        // Default constructor required for calls to DataSnapshot.getValue(Message.class)
    }

    public Message(String senderId, String senderName, String senderEmail, String messageText, String attachmentUrl, long timestamp) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.senderEmail = senderEmail;
        this.messageText = messageText;
        this.attachmentUrl = attachmentUrl;
        this.attachmentType = attachmentType;
        this.timestamp = timestamp;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
