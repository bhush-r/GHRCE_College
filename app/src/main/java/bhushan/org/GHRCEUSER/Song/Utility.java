//package bhushan.org.GHRCEUSER.Song;
//
//public class Utility {
//    public static String converDuration(long duration){
//        long minutes = (duration / 100) / 60;
//        long seconds = (duration / 100) % 60;
//        String converted = String.format("%d:%02d",minutes,seconds);
//        return converted;
//    }
//}


package bhushan.org.GHRCEUSER.Song;

public class Utility {
    public static String convertDuration(long duration){
        long minutes = (duration / 1000) / 60;
        long seconds = (duration / 1000) % 60;
        return String.format("%d:%02d", minutes, seconds);
    }
}
