package bhushan.org.GHRCEUSER.Song;

public class Song {
    private String name;
    private String url;
    private String imageUrl;

    public Song() {
    }

    public Song(String name, String url, String imageUrl) {
        this.name = name;
        this.url = url;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
