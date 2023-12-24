package bhushan.org.GHRCEUSER.paper;

public class PaperData {
    private String paperTitle, paperUri; //error in pdfUrl pdfUri

    public PaperData() {
    }

    public PaperData(String paperTitle, String paperUri) {
        this.paperTitle = paperTitle;
        this.paperUri = paperUri;
    }

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setName(String name) {
        this.paperTitle = name;
    }

    public String getPaperUri() {
        return paperUri;
    }

    public void setPdfUri(String pdfUri) {
        this.paperUri = paperUri;
    }
}
