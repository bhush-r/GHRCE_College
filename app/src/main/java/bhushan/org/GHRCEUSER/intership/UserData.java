package bhushan.org.GHRCEUSER.intership;

public class UserData {

    private String email;
    private String fullName;
    private String contactNo;
    private String college;
    private String gender;
    private String academicDegree;
    private String degreeStatus;
    private String internshipDomain;
    private String learnAbout;
    private String registrationOption;

    private String imageUrl; // Add imageUrl field



    public UserData() {
        // Default constructor required for calls to DataSnapshot.getValue(UserData.class)
    }

    public UserData(String email, String fullName, String contactNo, String college, String gender, String academicDegree,
                    String degreeStatus, String internshipDomain, String learnAbout, String registrationOption, String imageUrl) {
        this.email = email;
        this.fullName = fullName;
        this.contactNo = contactNo;
        this.college = college;
        this.gender = gender;
        this.academicDegree = academicDegree;
        this.degreeStatus = degreeStatus;
        this.internshipDomain = internshipDomain;
        this.learnAbout = learnAbout;
        this.registrationOption = registrationOption;
        this.imageUrl = imageUrl; // Initialize imageUrl
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAcademicDegree() {
        return academicDegree;
    }

    public void setAcademicDegree(String academicDegree) {
        this.academicDegree = academicDegree;
    }

    public String getDegreeStatus() {
        return degreeStatus;
    }

    public void setDegreeStatus(String degreeStatus) {
        this.degreeStatus = degreeStatus;
    }

    public String getInternshipDomain() {
        return internshipDomain;
    }

    public void setInternshipDomain(String internshipDomain) {
        this.internshipDomain = internshipDomain;
    }

    public String getLearnAbout() {
        return learnAbout;
    }

    public void setLearnAbout(String learnAbout) {
        this.learnAbout = learnAbout;
    }

    public String getRegistrationOption() {
        return registrationOption;
    }

    public void setRegistrationOption(String registrationOption) {
        this.registrationOption = registrationOption;

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;


    }
}
