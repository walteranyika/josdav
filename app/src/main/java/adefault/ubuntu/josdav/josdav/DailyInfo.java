package adefault.ubuntu.josdav.josdav;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;


// [START comment_class]
@IgnoreExtraProperties
public class DailyInfo {

    public String uid;
    public String author;
    public String feed_type;
    public String feed_quantity;
    public String mortality;
    public String temperature;


    public Map<String, Boolean> stars = new HashMap<>();



    public DailyInfo(String uid, String author, String feed_type,String feed_quantity,String mortality,String temperature) {
        this.uid = uid;
        this.author = author;
        this.feed_type = feed_type;
        this.feed_quantity=feed_quantity;
        this.mortality=mortality;
        this.temperature=temperature;

    }

    public DailyInfo() {
        // Default constructor required for calls to DataSnapshot.getValue(Comment.class)
    }
    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("feed_type",feed_type);
        result.put("feed_quantity",feed_quantity);
        result.put("mortality",mortality);
        result.put("temperature", temperature);

        // result.put("stars", stars);

        return result;
    }

}

