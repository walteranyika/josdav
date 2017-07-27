package adefault.ubuntu.josdav.josdav;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class NewCages extends BaseActivity  {
    // private static final String TAG = "Storage#MainActivity";

    private BroadcastReceiver mDownloadReceiver;
    private ProgressDialog mProgressDialog;
    private FirebaseAuth mAuth;


    // [START declare_ref]
    //private StorageReference mStorageRef;
    // [END declare_ref]
    private static final String TAG = "NewPostActivity";
    private static final String REQUIRED = "Required";

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]
    private EditText cageid,cagesize, cagestock, fishsize, fishtype, locality;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cage);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]
        // mStorageRef = FirebaseStorage.getInstance().getReference();

        cageid=(EditText)findViewById(R.id.cage_id);
        cagesize = (EditText) findViewById(R.id.cage_size);

        cagestock = (EditText) findViewById(R.id.cage_stock);
        fishsize = (EditText) findViewById(R.id.fish_size);
        fishtype = (EditText) findViewById(R.id.fish_type);
        locality = (EditText) findViewById(R.id.location);



        findViewById(R.id.fab_submit_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });
    }



    private void submitPost() {
        final  String cage_id=cageid.getText().toString();
        final String cage_size = cagesize.getText().toString();
        final String cage_stock = cagestock.getText().toString();
        final String fish_size = fishsize.getText().toString();
        final String fish_type = fishtype.getText().toString();
        final String location = locality.getText().toString();


        if (TextUtils.isEmpty(cage_id)) {
            cageid.setError(REQUIRED);
            return;
        }

        // Title is required
        if (TextUtils.isEmpty(cage_size)) {
            cagesize.setError(REQUIRED);
            return;
        }

        // Body is required
        if (TextUtils.isEmpty(cage_stock)) {
            cagestock.setError(REQUIRED);
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
        final String userId = getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);

                        // [START_EXCLUDE]
                        if (user == null) {
                            // User is null, error out
                            Log.e(TAG, "User " + userId + " is unexpectedly null");
                            Toast.makeText(NewCages.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new post
                            writeNewPost(userId, user.username,cage_id, cage_size, cage_stock, fish_size, fish_type
                                    , location);
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

    // [START write_fan_out]
    private void writeNewPost(String userId, String username,String cage_id, String cage_size,
                              String cage_stock, String fish_size, String fish_type, String location
    ) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("cages").push().getKey();
        Cage cage = new Cage(userId, username,cage_id, cage_size, cage_stock, fish_size, fish_type, location);
        Map<String, Object> postValues = cage.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/cages/" + key, postValues);
        childUpdates.put("/user-posts/" + userId + "/" + key+"/"+cage_id, postValues);

        mDatabase.updateChildren(childUpdates);
    }
    // [END write_fan_out]


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

}
