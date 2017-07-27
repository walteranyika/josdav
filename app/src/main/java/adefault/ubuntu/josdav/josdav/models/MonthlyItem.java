package adefault.ubuntu.josdav.josdav.models;

/**
 * Created by walter on 7/27/17.
 */

public class MonthlyItem {
    String month;
    String pondName;
    String feedType;
    int quantity;

    public MonthlyItem(String month, String pondName, String feetType, int quantity) {
        this.month = month;
        this.pondName = pondName;
        this.feedType = feetType;
        this.quantity = quantity;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getPondName() {
        return pondName;
    }

    public void setPondName(String pondName) {
        this.pondName = pondName;
    }

    public String getFeedType() {
        return feedType;
    }

    public void setFeedType(String feedType) {
        this.feedType = feedType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
