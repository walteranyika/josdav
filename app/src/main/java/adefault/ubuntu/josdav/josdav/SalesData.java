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
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import adefault.ubuntu.josdav.josdav.models.Sales;

public class SalesData extends BaseActivity implements View.OnClickListener {
    private static final String REQUIRED = "Required";
    ExpandableRelativeLayout  expandableLayout1;
    public static final String EXTRA_POST_KEY = "POND_KEY";
    private String mPostKey;
    private DatabaseReference pondRefrence,mostRecentRefrence;
    private Button mCommentButton;
    private EditText edtFishNumber, edtFishSize, edtFishPrice;
    private TextView tvNumber,tvWeight,tvPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_data);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPostKey = getIntent().getStringExtra(EXTRA_POST_KEY);
        if (mPostKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_POST_KEY");
        }

        // Initialize Database
        final String uid = getUid();
        edtFishNumber = (EditText) findViewById(R.id.number);
        edtFishSize = (EditText) findViewById(R.id.size);
        edtFishPrice = (EditText) findViewById(R.id.price);
        mCommentButton = (Button) findViewById(R.id.button_post_sales);
        mCommentButton.setOnClickListener(this);

        tvNumber= (TextView) findViewById(R.id.saleNumber);
        tvPrice= (TextView) findViewById(R.id.saleAmtReceived);
        tvWeight= (TextView) findViewById(R.id.saleFishWeight);

        mostRecentRefrence=FirebaseDatabase.getInstance().getReference().child("pondData/"+getUid()+"/"+mPostKey+"/dailySales");
        Query query= mostRecentRefrence.orderByKey().limitToLast(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("KEYS", ""+dataSnapshot.toString());
                for (DataSnapshot s: dataSnapshot.getChildren())
                {
                    Sales comment= s.getValue(Sales.class);
                    Log.d("KEYS", "" +comment.price);
                    tvWeight.setText("Fish Weight :"+comment.size);
                    tvNumber.setText("Number Harvested :"+comment.number);
                    tvPrice.setText("Amount Received :"+comment.price);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fcr: {

                Intent feedsIntent = new Intent(this, FCR.class);
                startActivity(feedsIntent);
            }

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    public void expandableButton4(View view) {
        expandableLayout1 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout1);
        expandableLayout1.toggle(); // toggle expand and collapse
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_post_sales:
                postASale();
                break;
        }
    }

    private void postASale() {
        final String uid = getUid();

        String number = edtFishNumber.getText().toString();
        String size = edtFishSize.getText().toString();
        String price = edtFishPrice.getText().toString();
        long messageTime = new Date().getTime();

        if (TextUtils.isEmpty(number)) {
            edtFishNumber.setError(REQUIRED);
            return;
        }

        // Body is required
        if (TextUtils.isEmpty( size)) {
            edtFishSize.setError(REQUIRED);
            return;
        }
        if (TextUtils.isEmpty(price)) {
            edtFishPrice.setError(REQUIRED);
            return;
        }

        Sales sales = new Sales(number, size, price,messageTime);
        pondRefrence = FirebaseDatabase.getInstance().getReference().child("pondData/"+getUid()+"/"+mPostKey+"/dailySales/"+messageTime);
        pondRefrence.setValue(sales).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Snackbar.make(findViewById(android.R.id.content), "Info Added Successfully", Snackbar.LENGTH_LONG).setActionTextColor(Color.BLUE).show();
                edtFishNumber.setText(null);
                edtFishSize.setText(null);
                edtFishPrice.setText(null);
            }
        });

    }
}

