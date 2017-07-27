package adefault.ubuntu.josdav.josdav;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by android on 7/16/2016.
 */
@IgnoreExtraProperties
public class Cage {



    public String uid;
    public String author;
    public String cage_id;
    public String cage_size;
    public String cage_stock;
    public String fish_size;
    public String fish_type;
    public String location;


    public Map<String, Boolean> stars = new HashMap<>();

    public Cage() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Cage(String uid, String author,String cage_id, String cage_size, String cage_stock, String fish_size, String fish_type, String location) {
        this.uid = uid;
        this.author = author;
        this.cage_id=cage_id;
        this.cage_size=cage_size;
        this.cage_stock = cage_stock;
        this.fish_size = fish_size;
        this.fish_type = fish_type;
        this.location = location;

    }


    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("cage_id",cage_id);
        result.put("cage_size",cage_size);
        result.put("cage_stock", cage_stock);
        result.put("fish_size", fish_size);
        result.put("fish_type", fish_type);
        result.put("location", location);

        result.put("stars", stars);

        return result;
    }

}
