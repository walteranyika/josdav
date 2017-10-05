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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import adefault.ubuntu.josdav.josdav.adapters.DailyFeedsListAdapter;
import adefault.ubuntu.josdav.josdav.adapters.FishGrowthListAdapter;
import adefault.ubuntu.josdav.josdav.models.DailyInput;
import adefault.ubuntu.josdav.josdav.models.FishGrowth;
import adefault.ubuntu.josdav.josdav.models.FishGrowthSummary;

public class FishGrowthReportActivity extends AppCompatActivity {
    ListView mListView;

    FishGrowthListAdapter mAdapter;
    private DatabaseReference mostRecentRefrence;
    ArrayList<FishGrowthSummary> pondSummary=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish_growth_report);
        getSupportActionBar().setTitle("Growth Monitor");
        mListView= (ListView) findViewById(R.id.fishGrowthListView);
        mAdapter=new FishGrowthListAdapter(this, pondSummary);
        mListView.setAdapter(mAdapter);
        mostRecentRefrence= FirebaseDatabase.getInstance().getReference().child("pondData/"+getUid());//+
        mostRecentRefrence.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0){
                    for (DataSnapshot pond: dataSnapshot.getChildren()){
                        Log.d("PONDS", pond.getKey()) ;
                        getPondReport(pond.getKey());
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void getPondReport(String key) {
        final String pondName =key;
        DatabaseReference pondRef = FirebaseDatabase.getInstance().getReference().child("pondData/"+getUid()+"/"+key+"/dailyFeeds");
        Query last21 = pondRef.orderByKey().limitToLast(21);
        last21.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<DailyInput> dailyArray=new ArrayList<DailyInput>();
                Log.d("CHILDREN_COUNT", "onDataChange: "+dataSnapshot.getChildrenCount());
                for (DataSnapshot val : dataSnapshot.getChildren()){
                    DailyInput input = val.getValue(DailyInput.class);
                    Log.d("CHILDREN_COUNT", "onDataChange: "+input.getFish_size());
                    dailyArray.add(input);
                }
                if(dailyArray.size()>=21){
                    DailyInput first = dailyArray.get(0);
                    DailyInput last = dailyArray.get(20);
                    double feedQty =0;
                    for (DailyInput input: dailyArray){
                        feedQty+= input.getFeed_quantity();
                    }
                    double fcr = feedQty/(last.getFish_size()-first.getFish_size());
                    double adg = (last.getFish_size()-first.getFish_size())/21;
                    FishGrowthSummary item =new FishGrowthSummary(pondName, first.getFish_size(), last.getFish_size(), feedQty, fcr,adg);
                    pondSummary.add(item);
                    mAdapter.notifyDataSetChanged();
                }
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
