package adefault.ubuntu.josdav.josdav.models;

public class MonthlyData {
    String feed_quantity;
    String feed_type;
    long messageTime;
    String mood;
    String mortality;
    String temperature;
    String pond_name;
    String month;
   public  MonthlyData(){}

    public MonthlyData(String feed_quantity, String feed_type, long messageTime, String mood, String mortality, String temperature) {

        this.feed_quantity = feed_quantity;
        this.feed_type = feed_type;
        this.messageTime = messageTime;
        this.mood = mood;
        this.mortality = mortality;
        this.temperature = temperature;
    }

    public MonthlyData(String feed_quantity, String feed_type, long messageTime, String mood, String mortality, String temperature, String pond_name) {
        this.feed_quantity = feed_quantity;
        this.feed_type = feed_type;
        this.messageTime = messageTime;
        this.mood = mood;
        this.mortality = mortality;
        this.temperature = temperature;
        this.pond_name = pond_name;
    }

    public MonthlyData(String feed_quantity, String feed_type, long messageTime, String mood, String mortality, String temperature, String pond_name, String month) {
        this.feed_quantity = feed_quantity;
        this.feed_type = feed_type;
        this.messageTime = messageTime;
        this.mood = mood;
        this.mortality = mortality;
        this.temperature = temperature;
        this.pond_name = pond_name;
        this.month = month;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getFeed_quantity() {
        return feed_quantity;
    }

    public void setFeed_quantity(String feed_quantity) {
        this.feed_quantity = feed_quantity;
    }

    public String getFeed_type() {
        return feed_type;
    }

    public void setFeed_type(String feed_type) {
        this.feed_type = feed_type;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getMortality() {
        return mortality;
    }

    public void setMortality(String mortality) {
        this.mortality = mortality;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getPond_name() {
        return pond_name;
    }

    public void setPond_name(String pond_name) {
        this.pond_name = pond_name;
    }
}
