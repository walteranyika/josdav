package adefault.ubuntu.josdav.josdav.models;
/**
 * Created by walter on 10/5/17.
 */
public class DailyInput {
    public String feed_type;
    public double feed_quantity;
    public double temperature;
    public double fish_size;
    public int mortality;
    public String mood;
    public long messageTime;

    public DailyInput() {
    }

    public DailyInput(String feed_type, double feed_quantity, double temperature, double fish_size, int mortality, String mood, long messageTime) {
        this.feed_type = feed_type;
        this.feed_quantity = feed_quantity;
        this.temperature = temperature;
        this.fish_size = fish_size;
        this.mortality = mortality;
        this.mood = mood;
        this.messageTime = messageTime;
    }

    public String getFeed_type() {
        return feed_type;
    }

    public void setFeed_type(String feed_type) {
        this.feed_type = feed_type;
    }

    public double getFeed_quantity() {
        return feed_quantity;
    }

    public void setFeed_quantity(double feed_quantity) {
        this.feed_quantity = feed_quantity;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getFish_size() {
        return fish_size;
    }

    public void setFish_size(double fish_size) {
        this.fish_size = fish_size;
    }

    public int getMortality() {
        return mortality;
    }

    public void setMortality(int mortality) {
        this.mortality = mortality;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
