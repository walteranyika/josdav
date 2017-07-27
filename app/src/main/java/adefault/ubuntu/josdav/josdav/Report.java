
package adefault.ubuntu.josdav.josdav;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

// [START comment_class]
@IgnoreExtraProperties
public class Report {

    public String uid;


    public String feed_type;
    public String feed_quantity;
    public String temperature;
    public String mortality;

    public long messageTime;
    public Report() {
        // Default constructor required for calls to DataSnapshot.getValue(Comment.class)
    }


    public Report(String uid, String feed_type, String feed_quantity, String temperature, String mortality,long messageTime) {
        this.uid = uid;


        this.feed_type=feed_type;
        this.feed_quantity = feed_quantity;
        this.temperature = temperature;
        this.mortality = mortality;

        this.messageTime=new Date().getTime();
    }
    public long getMessageTime() {
        return messageTime;
    }

    public String getMortality() {
        return mortality;
    }
    public String getUid() {
        return uid;
    }
    public String getFeed_type() {
        return feed_type;
    }

    public String getFeed_quantity() {
        return feed_quantity;
    }
}