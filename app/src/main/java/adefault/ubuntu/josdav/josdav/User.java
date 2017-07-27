package adefault.ubuntu.josdav.josdav;


import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START blog_user_class]
@IgnoreExtraProperties
public class User {

    public String username;

    public String fullname;
    public  String phone1;
    public  String phone2;
    public  String home;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username,String fullname, String phone1, String phone2,String home) {

        this.username = username;
        this.fullname = fullname;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.home=home;
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("username", username);
        result.put("fullname", fullname);
        result.put("phone1", phone1);
        result.put("phone2", phone2);
        result.put("home",home);

        return result;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



   // public void setMessageTime(long messageTime) {
       // this.messageTime = messageTime;
    //}
}

