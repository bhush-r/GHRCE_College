package bhushan.org.GHRCEUSER.menuitems.News;
import bhushan.org.GHRCEUSER.R;
public class Newss {
    public String title;
    public String notice;
    public String link;
    public String date;
    public long timestamp;
    public String notification;

    // Default constructor required for calls to DataSnapshot.getValue(News.class)
    public Newss() {
    }

    @Override
    public String toString() {
        return "Newss{" +
                "title='" + title + '\'' +
                ", notice='" + notice + '\'' +
                ", link='" + link + '\'' +
                ", date='" + date + '\'' +
                ", timestamp=" + timestamp +
                ", notification='" + notification + '\'' +
                '}';
    }
}

