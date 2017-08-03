
package adefault.ubuntu.josdav.josdav.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// [START comment_class]
@IgnoreExtraProperties
public class Comment {

    public String uid;
    public String author;
    public String genre;

    public String feed_type;
    public String feed_quantity;
    public String temperature;
    public String mortality;
    public String mood;
    public long messageTime;

    private  String pondId;

    public Comment(String feed_type, String feed_quantity, long messageTime, String pondId) {
        this.feed_type = feed_type;
        this.feed_quantity = feed_quantity;
        this.messageTime = messageTime;
        this.pondId = pondId;
    }

    public Comment(String feed_type, String feed_quantity, String temperature, String mortality, String mood, long messageTime) {
        this.feed_type = feed_type;
        this.feed_quantity = feed_quantity;
        this.temperature = temperature;
        this.mortality = mortality;
        this.mood = mood;
        this.messageTime = messageTime;
    }

    public String getPondId() {
        return pondId;
    }

    public Comment() {
        // Default constructor required for calls to DataSnapshot.getValue(Comment.class)
    }



    public Comment(String uid, String author, String feed_type, String feed_quantity,  String temperature, String mortality, String genre           , String mood, long messageTime) {
        this.uid = uid;
        this.genre = genre;
        this.author = author;
        this.feed_type = feed_type;
        this.feed_quantity = feed_quantity;
        this.temperature = temperature;
        this.mortality = mortality;
        this.mood = mood;
        this.messageTime = new Date().getTime();
    }

    public long getMessageTime() {
        return messageTime;
    }

    public String getFormattedDate(long time) {
        Date date = new Date(time);
        DateFormat formatter = new SimpleDateFormat("dd-MM-yy");
        String dateFormatted = formatter.format(date);
        return dateFormatted;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getFeed_type() {
        return feed_type;
    }

    public void setFeed_type(String feed_type) {
        this.feed_type = feed_type;
    }

    public String getFeed_quantity() {
        return feed_quantity;
    }

    public void setFeed_quantity(String feed_quantity) {
        this.feed_quantity = feed_quantity;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getMortality() {
        return mortality;
    }

    public void setMortality(String mortality) {
        this.mortality = mortality;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}