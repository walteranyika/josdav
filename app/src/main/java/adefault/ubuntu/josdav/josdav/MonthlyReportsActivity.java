package adefault.ubuntu.josdav.josdav;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import adefault.ubuntu.josdav.josdav.adapters.MonthlyFeedsListAdapter;
import adefault.ubuntu.josdav.josdav.databases.TemporaryMonthlyDb;
import adefault.ubuntu.josdav.josdav.models.MonthlyData;
import adefault.ubuntu.josdav.josdav.models.MonthlyItem;

public class MonthlyReportsActivity extends AppCompatActivity {
    DatabaseReference mostRecentRefrence;
    ListView monthlyFeedsListView;
    ArrayList<MonthlyItem> data;
    MonthlyFeedsListAdapter mMonthlyFeedsListAdapter;
    TemporaryMonthlyDb db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_reports);
        data=new ArrayList<>();
        monthlyFeedsListView= (ListView) findViewById(R.id.monthlyFeedsListView);
        mMonthlyFeedsListAdapter=new MonthlyFeedsListAdapter(this,data);
        monthlyFeedsListView.setAdapter(mMonthlyFeedsListAdapter);


        db=new TemporaryMonthlyDb(this);
        mostRecentRefrence= FirebaseDatabase.getInstance().getReference().child("pondData/"+getUid());//+"/"+mPostKey+"/dailyFeeds");

        mostRecentRefrence.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("KEYS", ""+dataSnapshot.toString());
                for (DataSnapshot s: dataSnapshot.getChildren())
                {
                    Log.d("TOP_LEVEL",""+s.toString());
                    String pond_title =s.getKey().replace("_"," ").toUpperCase();
                    for (DataSnapshot k: s.getChildren())
                    {
                        if(k.getKey().equals("dailyFeeds"))
                        {
                            for(DataSnapshot last:k.getChildren())
                            {
                                Log.d("LAST",pond_title+" "+last.toString());
                                MonthlyData item = last.getValue(MonthlyData.class);
                                item.setPond_name(pond_title);
                                db.saveData(item);
                                Log.d("INSERTED","Item Inserted");
                            }
                        }
                    }
                }
                data.clear();
                data.addAll(db.getData());
                Log.d("DATA_SIZE",""+data.size());
                mMonthlyFeedsListAdapter.notifyDataSetChanged();
                Log.d("COUNT_DB","Inserted "+db.countItems());

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
