package adefault.ubuntu.josdav.josdav.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by walter on 8/3/17.
 */

public class MonthlySale {
    private String pondName;
    private double quantity;
    private double unitPrice;
    private double total;
    private long time;
    private String month;

    public MonthlySale() {
    }

    public MonthlySale(String pondName, double quantity, double unitPrice, double total, long time, String month) {
        this.pondName = pondName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.total = total;
        this.time = time;
        this.month = month;
    }

    public MonthlySale(String pondName, double total, String month) {
        this.pondName = pondName;
        this.total = total;
        this.month = month;
    }

    public String getPondName() {
        return pondName;
    }

    public void setPondName(String pondName) {
        this.pondName = pondName;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
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
    public String getFormattedDate(long time) {
        Date date = new Date(time);
        DateFormat formatter = new SimpleDateFormat("MM");
        String dateFormatted = formatter.format(date);
        return dateFormatted;
    }
}
