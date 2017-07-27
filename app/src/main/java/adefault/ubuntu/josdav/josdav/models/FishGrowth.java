package adefault.ubuntu.josdav.josdav.models;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by walter on 7/27/17.
 */

public class FishGrowth {
    String month;
    double size;
    String fishPond;
    String date;
    long time;

    public FishGrowth() {
    }

    public FishGrowth(double size, String fishPond) {
        this.month = convertMilsToMonth(System.currentTimeMillis());
        this.size = size;
        this.fishPond = fishPond;
        this.date = convertMilsToDate(System.currentTimeMillis());
        this.time = System.currentTimeMillis();
    }

    public FishGrowth(String month, double size, String fishPond, String date, long time) {
        this.month = month;
        this.size = size;
        this.fishPond = fishPond;
        this.date = date;
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getFishPond() {
        return fishPond;
    }

    public void setFishPond(String fishPond) {
        this.fishPond = fishPond;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public  String convertMilsToMonth(long timeInMills){
        Date date =new Date(timeInMills);
        SimpleDateFormat formatter=new SimpleDateFormat("MMMM");
        String month = formatter.format(date);
        return  month;
    }

    public  String convertMilsToDate(long timeInMills){
        Date date =new Date(timeInMills);
        SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
        String readableDate = formatter.format(date);
        return  readableDate;
    }
}
