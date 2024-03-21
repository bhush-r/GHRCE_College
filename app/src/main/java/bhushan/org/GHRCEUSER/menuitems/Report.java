package bhushan.org.GHRCEUSER.menuitems;

public class Report {
    private String subject;
    private String description;
    private String dateTime;
    private String fileUrl;

    public Report() {
        // Default constructor required for Firebase
    }

    public Report(String subject, String description, String dateTime, String fileUrl) {
        this.subject = subject;
        this.description = description;
        this.dateTime = dateTime;
        this.fileUrl = fileUrl;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}


