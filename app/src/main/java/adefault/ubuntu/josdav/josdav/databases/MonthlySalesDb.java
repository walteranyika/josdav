package adefault.ubuntu.josdav.josdav.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import adefault.ubuntu.josdav.josdav.models.MonthlyData;
import adefault.ubuntu.josdav.josdav.models.MonthlyItem;
import adefault.ubuntu.josdav.josdav.models.MonthlySale;

/**
 * Created by walter on 7/11/17.
 */

public class MonthlySalesDb extends SQLiteOpenHelper {

    public MonthlySalesDb(Context context) {
        super(context, "sales.db", null, 1);
    }

    /**
     *private String pondName;
     private double quantity;
     private double unitPrice;
     private double total;
     private long time;
     private String month;
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS sales" +
                        "(id INTEGER PRIMARY KEY, " +
                        "pond TEXT, " +
                        "messageTime REAL UNIQUE, " +
                        "quantity REAL, " +
                        "price REAL, " +
                        "total REAL, " +
                        "month TEXT, " +
                        "UNIQUE(messageTime) ON CONFLICT REPLACE)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql;
        sql = "DROP TABLE IF EXISTS sales";
        db.execSQL(sql);
        String query = "CREATE TABLE IF NOT EXISTS sales" +
                "(id INTEGER PRIMARY KEY, " +
                "pond TEXT, " +
                "messageTime REAL UNIQUE, " +
                "quantity REAL, " +
                "price REAL, " +
                "total REAL, " +
                "month TEXT, " +
                "UNIQUE(messageTime) ON CONFLICT REPLACE)";
        db.execSQL(query);
    }

    /**
     * Inserts Transaction into SQLite DB
     */
    public void saveData(MonthlySale product) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("pond", product.getPondName());
        values.put("messageTime", product.getTime());
        values.put("quantity", product.getQuantity());
        values.put("price", product.getUnitPrice());
        values.put("total", product.getTotal());
        values.put("month", product.getMonth());

        database.insert("sales", null, values);
    }


    /**
     * Fetches all transactions based on the code
     * @return
     */
    public ArrayList<MonthlySale> getData() {
            ArrayList<MonthlySale> data;
            data = new ArrayList<>();//0          1,          2,                3
            String selectQuery = "SELECT  month, SUM(total), pond FROM sales GROUP BY month, pond";
            SQLiteDatabase database = this.getReadableDatabase();
            Cursor cursor = database.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do
                {
                    MonthlySale item=new MonthlySale(cursor.getString(2),cursor.getDouble(1),cursor.getString(0));
                    data.add(item);
                } while (cursor.moveToNext());
            }
            database.close();
            return data;
    }


}
