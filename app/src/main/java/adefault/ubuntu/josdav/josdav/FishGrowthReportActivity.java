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

import adefault.ubuntu.josdav.josdav.adapters.DailyFeedsListAdapter;
import adefault.ubuntu.josdav.josdav.adapters.FishGrowthListAdapter;
import adefault.ubuntu.josdav.josdav.models.FishGrowth;

public class FishGrowthReportActivity extends AppCompatActivity {
    ListView mListView;
    ArrayList<FishGrowth> data;
    FishGrowthListAdapter mAdapter;
    private DatabaseReference mostRecentRefrence;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish_growth_report);
        getSupportActionBar().setTitle("Growth Monitor");


        mListView= (ListView) findViewById(R.id.fishGrowthListView);
        data=new ArrayList<>();
        mAdapter=new FishGrowthListAdapter(this, data);
        mListView.setAdapter(mAdapter);
        mostRecentRefrence= FirebaseDatabase.getInstance().getReference().child("pondData/"+getUid());//+

        mostRecentRefrence.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("KEYS", ""+dataSnapshot.toString());
                for (DataSnapshot s: dataSnapshot.getChildren())
                {
                    //Comment comment= s.getValue(Comment.class);
                    Log.d("TOP_LEVEL",""+s.toString());

                    for (DataSnapshot k: s.getChildren())
                    {
                        if(k.getKey().equals("fishGrowth"))
                        {
                            for(DataSnapshot last:k.getChildren())
                            {
                                FishGrowth item=last.getValue(FishGrowth.class);
                                data.add(item);
                            }
                        }
                    }
                }
                mAdapter.notifyDataSetChanged();
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
