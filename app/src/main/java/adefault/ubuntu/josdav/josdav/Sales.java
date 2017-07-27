package adefault.ubuntu.josdav.josdav;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

// [START comment_class]
@IgnoreExtraProperties
public class Sales {

    public String uid;
    public String author;
    public String number;
    public String size;
    public String price;

    public String genre;
    public long messageTime;

    public Sales() {
        // Default constructor required for calls to DataSnapshot.getValue(Comment.class)
    }

    public Sales(String number, String size, String price, long messageTime) {
        this.number = number;
        this.size = size;
        this.price = price;
        this.messageTime = messageTime;
    }

    public Sales(String uid, String author, String number, String size, String price, String genre, long messageTime) {
        this.uid = uid;
        this.author = author;
        this.number = number;
        this.size=size;
        this.price=price;

        this.genre=genre;

        this.messageTime=new Date().getTime();
    }
    public long getMessageTime() {
        return messageTime;
    }
}

