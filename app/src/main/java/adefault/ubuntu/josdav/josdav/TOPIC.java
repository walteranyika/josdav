package adefault.ubuntu.josdav.josdav;

/**
 * Created by android on 6/27/2017.
 */

public class TOPIC { private String uid;
    private String artistName;
    private String username;


    public TOPIC(){
        //this constructor is required
    }

    public TOPIC(String uid, String artistName, String username) {
        this.uid = uid;
        this.artistName = artistName;
this.username=username;
    }

    public String getUid() {
        return uid;
    }

    public String getArtistName() {
        return artistName;
    }
public  String getUsername(){return  username;}
}
