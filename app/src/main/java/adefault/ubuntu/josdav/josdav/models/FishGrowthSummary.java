package adefault.ubuntu.josdav.josdav.models;

/**
 * Created by walter on 10/5/17.
 */

public class FishGrowthSummary {
    String pondName;
    double prevWeight,currentWeight,feedQty,fcr,adg;

    public FishGrowthSummary() {
    }

    public FishGrowthSummary(String pondName, double prevWeight, double currentWeight, double feedQty, double fcr, double adg) {
        this.pondName = pondName;
        this.prevWeight = prevWeight;
        this.currentWeight = currentWeight;
        this.feedQty = feedQty;
        this.fcr = fcr;
        this.adg = adg;
    }

    public String getPondName() {
        return pondName;
    }

    public void setPondName(String pondName) {
        this.pondName = pondName;
    }

    public double getPrevWeight() {
        return prevWeight;
    }

    public void setPrevWeight(double prevWeight) {
        this.prevWeight = prevWeight;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(double currentWeight) {
        this.currentWeight = currentWeight;
    }

    public double getFeedQty() {
        return feedQty;
    }

    public void setFeedQty(double feedQty) {
        this.feedQty = feedQty;
    }

    public double getFcr() {
        return fcr;
    }

    public void setFcr(double fcr) {
        this.fcr = fcr;
    }

    public double getAdg() {
        return adg;
    }

    public void setAdg(double adg) {
        this.adg = adg;
    }
}
