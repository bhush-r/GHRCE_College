package bhushan.org.GHRCEUSER.ebook;

public class EbookData {
    private String pdfTitle, pdfUri; //error in pdfUrl pdfUri

    public EbookData() {
    }

    public EbookData(String pdfTitle, String pdfUri) {
        this.pdfTitle = pdfTitle;
        this.pdfUri = pdfUri;
    }

    public String getPdfTitle() {
        return pdfTitle;
    }

    public void setName(String name) {
        this.pdfTitle = name;
    }

    public String getPdfUri() {
        return pdfUri;
    }

    public void setPdfUri(String pdfUri) {
        this.pdfUri = pdfUri;
    }
}
