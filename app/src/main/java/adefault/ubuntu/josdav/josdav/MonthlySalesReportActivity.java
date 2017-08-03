package adefault.ubuntu.josdav.josdav;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import adefault.ubuntu.josdav.josdav.adapters.SalesListAdapter;
import adefault.ubuntu.josdav.josdav.databases.MonthlySalesDb;
import adefault.ubuntu.josdav.josdav.models.MonthlySale;
import adefault.ubuntu.josdav.josdav.models.Sales;

public class MonthlySalesReportActivity extends AppCompatActivity {
    ArrayList<MonthlySale> salesItems;
    MonthlySalesDb mMonthlySalesDb;
    ListView mListViewMonthlySales;
    SalesListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_sales_report);
        getSupportActionBar().setTitle("Monthly Sales");
        salesItems=new ArrayList<>();
        mMonthlySalesDb=new MonthlySalesDb(this);
        mListViewMonthlySales= (ListView) findViewById(R.id.list_monthly_sales);
        adapter=new SalesListAdapter(this, salesItems);
        mListViewMonthlySales.setAdapter(adapter);

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("pondData/" + getUid());
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("KEYS", "" + dataSnapshot.toString());
                for (DataSnapshot s : dataSnapshot.getChildren()) {
                    //Comment comment= s.getValue(Comment.class);
                    Log.d("TOP_LEVEL", "" + s.toString());
                    String pond = s.getKey().replace("_", " ").toUpperCase();
                    for (DataSnapshot k : s.getChildren()) {
                        if (k.getKey().equals("dailySales")) {
                            for (DataSnapshot last : k.getChildren()) {
                                Log.d("LAST", pond + " " + last.toString());
                                Sales item = last.getValue(Sales.class);
                                //(String pondName, double quantity, double unitPrice, double total, long time, String month)
                                try {
                                    double quantity = Double.parseDouble(item.getSize());
                                    double price = Double.parseDouble(item.getPrice());
                                    double total = quantity * price;
                                    String month = item.getFormattedMonth(item.getMessageTime());
                                    long time = item.getMessageTime();
                                    MonthlySale monthlySale = new MonthlySale(pond, quantity, price, total, time, month);
                                    mMonthlySalesDb.saveData(monthlySale);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            }
                        }
                    }
                }
                salesItems.clear();
                salesItems.addAll(mMonthlySalesDb.getData());
                adapter.notifyDataSetChanged();
                Log.d("COUNT", "onDataChange: "+salesItems.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

}
