package adefault.ubuntu.josdav.josdav;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by android on 7/16/2016.
 */
@IgnoreExtraProperties
public class Post {



    public String uid;

    public String username;
    public String tel1;
    public String tel2;
    public String locat;



    public Map<String, Boolean> stars = new HashMap<>();


    public Post(String uid, String username, String tel1, String tel2, String locat) {
        this.uid = uid;

        this.username=username;
        this.tel1=tel1;
        this.tel2=tel2;
        this.locat = locat;


    }
    public Post(){ }  //no argument constructor

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);

        result.put("username",username);
        result.put("tel1",tel1);
        result.put("tel2",tel2);
        result.put("locat", locat);


       // result.put("stars", stars);

        return result;
    }

}
