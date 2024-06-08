package bhushan.org.GHRCEUSER.compile;

public class JDoodleRequest {
    private String script;
    private String language;
    private String versionIndex;
    private String clientId;
    private String clientSecret;

    public JDoodleRequest(String script, String language, String versionIndex, String clientId, String clientSecret) {
        this.script = script;
        this.language = language;
        this.versionIndex = versionIndex;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }
}

