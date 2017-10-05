package adefault.ubuntu.josdav.josdav;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import adefault.ubuntu.josdav.josdav.models.DailyInput;
import adefault.ubuntu.josdav.josdav.utils.Utility;


public class PondDaily extends BaseActivity{
    ExpandableRelativeLayout  expandableLayout3;
    private static final String REQUIRED = "Required";
    TextView inputFeedType, inputQuantity;
    public static final String EXTRA_POST_KEY = "POND_KEY";
    private DatabaseReference pondRefrence,mostRecentRefrence;
    private String mPostKey;
    private Button mCommentButton;
    private EditText inputTemp, inputMortality,inputFishSize;
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
        inputFeedType = (TextView) findViewById(R.id.feed_type);
        inputQuantity = (TextView) findViewById(R.id.textInputLayout70);
        inputTemp = (EditText) findViewById(R.id.temperature);
        inputFishSize = (EditText) findViewById(R.id.inputFishSize);
        inputMortality = (EditText) findViewById(R.id.mortality);


        tvPondTitle= (TextView) findViewById(R.id.PondName);
        tvPondFeedType= (TextView) findViewById(R.id.PondFeedType);
        tvPondTemp= (TextView) findViewById(R.id.PondTemprature);
        tvPondMortality= (TextView) findViewById(R.id.PondMortality);
        tvPondMood= (TextView) findViewById(R.id.PondMood);
        tvPondQty= (TextView) findViewById(R.id.PondFeedQty);
        inputMortality.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String temp = inputTemp.getText().toString();
                String size = inputFishSize.getText().toString();
                if(temp.isEmpty()|| size.isEmpty()){

                }else{
                   double temprature = Double.parseDouble(temp);
                   double fishSize = Double.parseDouble(size);
                   Double[] data= Utility.read_data(fishSize,temprature);
                   Log.d("DATA_LOG",data[0]+" "+data[1]);
                   feedType=data[0];
                   feedQuantity=data[1];
                   inputFeedType.setText("Feed Type "+data[0]);
                   inputQuantity.setText("Feed Quantity "+data[1]);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //mCommentsRecycler = (RecyclerView) findViewById(R.id.dail_list);



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
                    DailyInput comment= s.getValue(DailyInput.class);
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


    double feedType,feedQuantity=0;
    private void postDailyFeed() {
        String temp = inputTemp.getText().toString();
        String size = inputFishSize.getText().toString();
        double fishSize,temprature=0;
        long messageTime = new Date().getTime();
        int mortality = 0;
        if(temp.isEmpty()|| size.isEmpty() || inputMortality.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Fill in all values", Toast.LENGTH_SHORT).show();
          return;
        }else{
            temprature = Double.parseDouble(temp);
            fishSize = Double.parseDouble(size);
            mortality=Integer.parseInt(inputMortality.getText().toString().trim());
        }
        DailyInput input=new DailyInput(feedType+"",feedQuantity,temprature,fishSize, mortality,mood,messageTime);
        pondRefrence = FirebaseDatabase.getInstance().getReference().child("pondData/"+getUid()+"/"+mPostKey+"/dailyFeeds/"+messageTime);
        pondRefrence.setValue(input).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Snackbar.make(findViewById(android.R.id.content), "Info Added Successfully", Snackbar.LENGTH_LONG).setActionTextColor(Color.BLUE).show();
                inputQuantity.setText(null);
                inputMortality.setText(null);
                inputTemp.setText(null);
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
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(inputMortality.getWindowToken(), 0);
            postDailyFeed();
        }
        return super.onOptionsItemSelected(item);
    }
}

