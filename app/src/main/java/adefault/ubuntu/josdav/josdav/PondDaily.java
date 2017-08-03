package adefault.ubuntu.josdav.josdav;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import adefault.ubuntu.josdav.josdav.models.Comment;


public class PondDaily extends BaseActivity implements View.OnClickListener {
    ExpandableRelativeLayout  expandableLayout3;
    private static final String REQUIRED = "Required";
    Spinner feed;
    public static final String EXTRA_POST_KEY = "POND_KEY";
    private DatabaseReference pondRefrence,mostRecentRefrence;
    private String mPostKey;
    private Button mCommentButton;
    private EditText quant,temp,morta;
    private RadioGroup radio1;

    TextView tvPondTitle, tvPondFeedType, tvPondTemp, tvPondMortality, tvPondMood,tvPondQty;
    String mood="Good";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily2);
        getSupportActionBar().setTitle("Daily Data");
        mPostKey = getIntent().getStringExtra(EXTRA_POST_KEY);
        final String uid = getUid();
        radio1 = (RadioGroup) findViewById(R.id.radro);
        feed = (Spinner) findViewById(R.id.feed_type);
        quant = (EditText) findViewById(R.id.feed_quantity);
        temp = (EditText) findViewById(R.id.temperature);
        morta = (EditText) findViewById(R.id.mortality);
        mCommentButton = (Button) findViewById(R.id.button_post_comment);

        tvPondTitle= (TextView) findViewById(R.id.PondName);
        tvPondFeedType= (TextView) findViewById(R.id.PondFeedType);
        tvPondTemp= (TextView) findViewById(R.id.PondTemprature);
        tvPondMortality= (TextView) findViewById(R.id.PondMortality);
        tvPondMood= (TextView) findViewById(R.id.PondMood);
        tvPondQty= (TextView) findViewById(R.id.PondFeedQty);

        //mCommentsRecycler = (RecyclerView) findViewById(R.id.dail_list);
        mCommentButton.setOnClickListener(this);
        radio1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
               if(checkedId==R.id.Happy){
                 mood="Happy";
               }
               else if(checkedId==R.id.Dull)
               {
                   mood="Dull";
               }

            }
        });

        mostRecentRefrence=FirebaseDatabase.getInstance().getReference().child("pondData/"+getUid()+"/"+mPostKey+"/dailyFeeds");
        Query query= mostRecentRefrence.orderByKey().limitToLast(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("KEYS", ""+dataSnapshot.toString());
                for (DataSnapshot s: dataSnapshot.getChildren())
                {
                    Comment comment= s.getValue(Comment.class);
                    Log.d("KEYS", "" + comment.temperature);
                    tvPondFeedType.setText("Feed Type :"+comment.feed_type);
                    tvPondQty.setText("Quantity :"+comment.feed_quantity);
                    tvPondMood.setText("Mood is :"+comment.mood);
                    tvPondTemp.setText("Temprature is :"+comment.temperature);
                    tvPondMortality.setText("Mortality rate is :"+comment.mortality);
                    tvPondTitle.setText(mPostKey.replace("_"," ").toUpperCase());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
    public void expandableButton4(View view) {
        expandableLayout3 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout3);
        expandableLayout3.toggle(); // toggle expand and collapse
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_post_comment:
                postDailyFeed();
                break;
        }
    }

    private void postDailyFeed() {
        String feed_type=feed.getSelectedItem().toString();
        String feed_quantity = quant.getText().toString();
        String temperature = temp.getText().toString();
        String mortality = morta.getText().toString();

        long messageTime = new Date().getTime();
        // Body is required
        if (TextUtils.isEmpty( feed_quantity)) {
            quant.setError(REQUIRED);
            return;
        }
        if (TextUtils.isEmpty(temperature)) {
            temp.setError(REQUIRED);
            return;
        }
        // Body is required
        if (TextUtils.isEmpty(mortality)) {
            morta.setError(REQUIRED);
            return;
        }


        Comment comment = new Comment(feed_type, feed_quantity, temperature,mortality,mood,messageTime);
        pondRefrence = FirebaseDatabase.getInstance().getReference().child("pondData/"+getUid()+"/"+mPostKey+"/dailyFeeds/"+messageTime);
        pondRefrence.setValue(comment).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Snackbar.make(findViewById(android.R.id.content), "Info Added Successfully", Snackbar.LENGTH_LONG).setActionTextColor(Color.BLUE).show();
                quant.setText(null);
                morta.setText(null);
                temp.setText(null);
            }
        });
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.daily_growth_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_item_add_daily_growth){
            Intent intent=new Intent(this,FishGrowthActivity.class);
            intent.putExtra(EXTRA_POST_KEY,mPostKey);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}

