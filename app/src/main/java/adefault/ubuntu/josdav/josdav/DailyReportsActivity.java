package adefault.ubuntu.josdav.josdav;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
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

public class DailyReportsActivity extends AppCompatActivity {
    ListView mListView;
    ArrayList<Comment> data;
    DailyFeedsListAdapter mAdapter;
    private DatabaseReference mostRecentRefrence;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_reports);
        getSupportActionBar().setTitle("Daily Feeds");
        mListView= (ListView) findViewById(R.id.dailyFeedsListView);
        data=new ArrayList<>();
        mAdapter=new DailyFeedsListAdapter(this, data);
        mListView.setAdapter(mAdapter);
        mostRecentRefrence= FirebaseDatabase.getInstance().getReference().child("pondData/"+getUid());//+"/"+mPostKey+"/dailyFeeds");

        mostRecentRefrence.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("KEYS", ""+dataSnapshot.toString());
                for (DataSnapshot s: dataSnapshot.getChildren())
                {
                    //Comment comment= s.getValue(Comment.class);
                    Log.d("TOP_LEVEL",""+s.toString());
                    String pond_title =s.getKey().replace("_"," ").toUpperCase();
                    for (DataSnapshot k: s.getChildren())
                    {
                       if(k.getKey().equals("dailyFeeds"))
                       {
                         for(DataSnapshot last:k.getChildren())
                         {
                             Log.d("LAST",pond_title+" "+last.toString());
                             Comment major = last.getValue(Comment.class);
                             //String feed_type, String feed_quantity, long messageTime, String pondId
                             Comment yetu = new Comment(major.feed_type,major.feed_quantity,major.messageTime,pond_title);
                             data.add(yetu);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.searchable_menu, menu);

       /* SearchView searchView =(SearchView) menu.findItem(R.id.menu_search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("SEARCH", newText);
                mAdapter.filter(newText);
                return false;
            }
        });*/
        return super.onCreateOptionsMenu(menu);
    }
}
