package bhushan.org.GHRCEUSER.paper;

public class PaperData {
    private String paperTitle, paperUri;
    private String branch;
    private String semester;

    public PaperData() {
    }

    public PaperData(String paperTitle, String paperUri, String branch, String semester) {
        this.paperTitle = paperTitle;
        this.paperUri = paperUri;
        this.branch = branch;
        this.semester = semester;
    }

    // Getters and setters
    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }

    public String getPaperUri() {
        return paperUri;
    }

    public void setPaperUri(String paperUri) {
        this.paperUri = paperUri;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
