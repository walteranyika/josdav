package adefault.ubuntu.josdav.josdav;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Belal on 2/26/2017.
 */
@IgnoreExtraProperties
public class Answers {
    private String id;
    private String trackNames;
    private String username;

    public Answers() {

    }

    public Answers(String id, String trackNames, String username) {
        this.trackNames = trackNames;
        this.username = username;
        this.id = id;
    }

    public String getTrackNames() {
        return trackNames;
    }

    public String getUsername() {
        return username;
    }
}
