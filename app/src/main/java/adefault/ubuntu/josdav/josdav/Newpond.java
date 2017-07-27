package adefault.ubuntu.josdav.josdav;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Newpond extends BaseActivity  {
    // private static final String TAG = "Storage#MainActivity";

    private BroadcastReceiver mDownloadReceiver;
    private ProgressDialog mProgressDialog;


    private RadioGroup radio;
    private RadioButton radioSexButton;


    // [START declare_ref]
    //private StorageReference mStorageRef;
    // [END declare_ref]
    private static final String TAG = "NewPostActivity";
    private static final String REQUIRED = "Required";
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    // [START declare_database_ref]
    private DatabaseReference mDatabase,pondInfoRef;
    // [END declare_database_ref]
    private EditText pondid,pondsize, pondstock, fishsize, fishtype, locality;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pond);
        //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        radio = (RadioGroup) findViewById(R.id.fish);
        //  radio.clearCheck();
        pondid = (EditText) findViewById(R.id.pond_id);
        pondsize = (EditText) findViewById(R.id.pond_size);
        pondstock = (EditText) findViewById(R.id.pond_stock);
        fishsize = (EditText) findViewById(R.id.fish_size);
        fishtype = (EditText) findViewById(R.id.fish_type);
        locality = (EditText) findViewById(R.id.location);
        // RadioButton rb = (RadioButton) radio.findViewById(radio.getCheckedRadioButtonId());
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    // Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    //  toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    toastMessage("Successfully signed out.");
                }

            }};
        findViewById(R.id.fab_submit_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });

        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    Toast.makeText(Newpond.this, rb.getText(), Toast.LENGTH_SHORT).show();
                    //RadioButton rb = (RadioButton) radio.findViewById(radio.getCheckedRadioButtonId());
                    // RadioGroup rg = (RadioGroup)findViewById(R.id.fish);
                    // String rg = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();
                }

            }
        });

    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    public void onClear(View v) {
        /* Clears all selected radio buttons to default */
        radio.clearCheck();
    }
    // Toast.makeText(MainActivity.this, rb.getText(), Toast.LENGTH_SHORT).show();

    private void submitPost() {
        final String fish = ((RadioButton) this.findViewById(radio.getCheckedRadioButtonId())).getText().toString();

        final String pond_id = pondid.getText().toString();
        final String pond_size = pondsize.getText().toString();
        final String pond_stock = pondstock.getText().toString();
        final String fish_size = fishsize.getText().toString();
        final String fish_type = fishtype.getText().toString();
        final String location = locality.getText().toString();

        if (TextUtils.isEmpty(pond_id)) {
            pondid.setError(REQUIRED);
            return;
        }

        // Title is required
        if (TextUtils.isEmpty(pond_size)) {
            pondsize.setError(REQUIRED);
            return;
        }

        // Body is required
        if (TextUtils.isEmpty(pond_stock)) {
            pondstock.setError(REQUIRED);
            return;
        }
        if (TextUtils.isEmpty(fish_size)) {
            fishsize.setError(REQUIRED);
            return;
        }

        // Body is required
        if (TextUtils.isEmpty(fish_type)) {
            fishtype.setError(REQUIRED);
            return;
        }

        if (TextUtils.isEmpty(location)) {
            locality.setError(REQUIRED);
            return;
        }

        // [START single_value_read]
        final FirebaseUser user = mAuth.getCurrentUser();
        final String userId = getUid();
        mDatabase.child(userId).child("APonds").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        // User user = dataSnapshot.getValue(User.class);

                        // [START_EXCLUDE]
                        if (user == null) {
                            // User is null, error out
                            Log.e(TAG, "User " + userId + " is unexpectedly null");
                            Toast.makeText(Newpond.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {

                            // Write new post
                            String pond_name = pond_id.replace(" ","_").toLowerCase();
                            pondInfoRef=myRef.child("pondData/"+getUid()+"/"+pond_name+"/details");
                            // Write new post
                            Pond p=new Pond(fish,pond_id,pond_size,pond_stock,fish_size,fish_type,location);
                            pondInfoRef.setValue(p);
                           // writeNewPost(userId,  fish, pond_id, pond_size, pond_stock, fish_size, fish_type, location);
                        }

                        // Finish this Activity, back to the stream

                        finish();


                        // [END_EXCLUDE]
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });
        // [END single_value_read]
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    // [START write_fan_out]
    private void writeNewPost(String userId, String fish, String pond_id, String pond_size,
                              String pond_stock, String fish_size, String fish_type, String location
    ) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("APonds").push().getKey();
        Pond post = new Pond(userId, fish, pond_id, pond_size, pond_stock, fish_size, fish_type, location);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/APonds/" + key, postValues);
        childUpdates.put("/my-Ponds/" + userId + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }
    // [END write_fan_out]


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}