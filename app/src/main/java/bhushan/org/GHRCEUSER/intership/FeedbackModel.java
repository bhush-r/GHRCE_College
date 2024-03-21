package bhushan.org.GHRCEUSER.intership;

public class FeedbackModel {
    private String name;
    private String issue;
    private String selectedFileName;

    public FeedbackModel() {
        // Default constructor required for calls to DataSnapshot.getValue(FeedbackModel.class)
    }

    public FeedbackModel(String name, String issue, String selectedFileName) {
        this.name = name;
        this.issue = issue;
        this.selectedFileName = selectedFileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getSelectedFileName() {
        return selectedFileName;
    }

    public void setSelectedFileName(String selectedFileName) {
        this.selectedFileName = selectedFileName;
    }
}
