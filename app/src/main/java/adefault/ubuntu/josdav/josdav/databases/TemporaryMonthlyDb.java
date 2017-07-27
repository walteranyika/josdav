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

/**
 * Created by walter on 7/11/17.
 */

public class TemporaryMonthlyDb extends SQLiteOpenHelper {

    public TemporaryMonthlyDb(Context context) {
        super(context, "monthly.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS monthly" +
                "(id INTEGER PRIMARY KEY, " +
                "feed_quantity INTEGER, " +
                "feed_type TEXT, " +
                "messageTime REAL UNIQUE, " +
                "mood TEXT, " +
                "mortality INT, " +
                "temperature INT, " +
                "month TEXT, " +
                "pond_name TEXT, UNIQUE(messageTime) ON CONFLICT REPLACE)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql;
        sql = "DROP TABLE IF EXISTS monthly";
        db.execSQL(sql);
        String query = "CREATE TABLE IF NOT EXISTS monthly" +
                "(id INTEGER PRIMARY KEY, " +
                "feed_quantity INTEGER, " +
                "feed_type TEXT, " +
                "messageTime REAL, " +
                "mood TEXT, " +
                "mortality INT, " +
                "temperature INT, " +
                "month TEXT, " +
                "pond_name TEXT,  UNIQUE(messageTime) ON CONFLICT REPLACE))";
        db.execSQL(query);
    }

    /**
     * Inserts Transaction into SQLite DB
     */
    public void saveData(MonthlyData product) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("feed_quantity", convertToInt(product.getFeed_quantity()));
        values.put("feed_type", product.getFeed_type());
        values.put("messageTime", product.getMessageTime());
        values.put("mood", product.getMood());
        values.put("mortality", convertToInt(product.getMortality()));
        values.put("month", convertToMonth(product.getMessageTime()));
        values.put("temperature", convertToInt(product.getTemperature()));
        values.put("pond_name", product.getPond_name().replace("_", " ").toUpperCase());
        database.insert("monthly", null, values);
    }


    /**
     * Fetches all transactions based on the code
     * @return
     */
    public ArrayList<MonthlyItem> getData() {
            ArrayList<MonthlyItem> data;
            data = new ArrayList<>();//0          1,          2,                3
            String selectQuery = "SELECT  month, feed_type, SUM(feed_quantity), pond_name FROM monthly GROUP BY month,feed_type";
            SQLiteDatabase database = this.getReadableDatabase();
            Cursor cursor = database.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do
                {
                    MonthlyItem item=new MonthlyItem(cursor.getString(0),cursor.getString(3),cursor.getString(1),cursor.getInt(2));
                    data.add(item);
                } while (cursor.moveToNext());
            }
            database.close();
            return data;
    }



    public String convertToMonth(long time) {
        Date date = new Date(time);
        SimpleDateFormat df2 = new SimpleDateFormat("MMMM");
        String dateText = df2.format(date);
        return dateText;
    }

    public int convertToInt(String str) {
        int val = 0;
        String value = str.replaceAll("\\D+", "");
        try {
            val=Integer.parseInt(value);
        }catch (NumberFormatException e)
        {
            Log.d("NUMBER_ERROR","Error converting "+str);
        }
        return val;
    }
    public int countItems() {
        int count = 0;
        String sql = "SELECT  COUNT(*) FROM monthly";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        database.close();
        return count;
    }

}
