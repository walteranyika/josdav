package adefault.ubuntu.josdav.josdav;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;

/**
 * Created by android on 7/16/2016.
 */
@IgnoreExtraProperties
public class Pond {



    public String uid;

    public String fish;
    public String pond_id;
    public String pond_size;
    public String pond_stock;
    public String fish_size;
    public String fish_type;
    public String location;



    public Map<String, Boolean> stars = new HashMap<>();

    public Pond(String fish, String pond_id, String pond_size, String pond_stock, String fish_size, String fish_type, String location) {
        this.fish = fish;
        this.pond_id = pond_id;
        this.pond_size = pond_size;
        this.pond_stock = pond_stock;
        this.fish_size = fish_size;
        this.fish_type = fish_type;
        this.location = location;
    }

    public Pond(String uid, String fish,String pond_id,String pond_size, String pond_stock, String fish_size,
                String fish_type, String location) {
        this.uid = uid;

        this.fish=fish;
        this.pond_id=pond_id;
        this.pond_size=pond_size;
        this.pond_stock = pond_stock;
        this.fish_size = fish_size;
        this.fish_type = fish_type;
        this.location = location;


    }
    public Pond (){ }  //no argument constructor

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);

        result.put("fish",fish);
        result.put("pond_id",pond_id);
        result.put("pond_size",pond_size);
        result.put("pond_stock", pond_stock);
        result.put("fish_size", fish_size);
        result.put("fish_type", fish_type);
        result.put("location", location);


       // result.put("stars", stars);

        return result;
    }
    public String getPond_id() {
        return pond_id;
    }
}
