package adefault.ubuntu.josdav.josdav;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Belal on 2/26/2017.
 */
@IgnoreExtraProperties
public class Answer {
    private String id;
    private String trackName;
    private String username;

    public Answer() {

    }

    public Answer(String id, String trackName, String username) {
        this.trackName = trackName;
        this.username = username;
        this.id = id;
    }

    public String getTrackName() {
        return trackName;
    }

    public String getUsername() {
        return username;
    }
}
