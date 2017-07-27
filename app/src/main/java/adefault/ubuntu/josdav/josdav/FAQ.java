package adefault.ubuntu.josdav.josdav;

/**
 * Created by android on 6/27/2017.
 */

public class FAQ {
    private String uid;
    private String artistName;
    private String username;


    public FAQ() {
        //this constructor is required
    }

    public FAQ(String uid, String artistName, String username) {
        this.uid = uid;
        this.artistName = artistName;
        this.username = username;
    }

    public String getUid() {
        return uid;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getUsername() {
        return username;
    }
}
