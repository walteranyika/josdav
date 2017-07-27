package adefault.ubuntu.josdav.josdav;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserP extends BaseActivity implements
        View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "ProfileActivity";
    private static final String REQUIRED = "Required";

    private ViewGroup mProfileUi;
    private ViewGroup mSignInUi;
    private FirebaseAuth mAuth;
    private CircleImageView mProfilePhoto;
    private TextView mProfileUsername, fn, ph1, ph2, co;
    private GoogleApiClient mGoogleApiClient;
    private ValueEventListener mPostListener;
    private static final int RC_SIGN_IN = 103;
    Button buttoni, bu;
    private DatabaseReference mDatabase;

    DatabaseReference databaseTracks;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_p);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        databaseTracks = FirebaseDatabase.getInstance().getReference("user");

        buttoni = (Button) findViewById(R.id.dialog);
        //bu = (Button) findViewById(R.id.refresh);

        buttoni.setOnClickListener(this);
        //  bu.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        // GoogleApiClient with Sign In
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,
                        new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestEmail()
                                .requestIdToken(getString(R.string.default_web_client_id))
                                .build())
                .build();

        //  mSignInUi = (ViewGroup) findViewById(R.id.sign_in_ui);
        mProfileUi = (ViewGroup) findViewById(R.id.profile);

        mProfilePhoto = (CircleImageView) findViewById(R.id.profile_user_photo);
        mProfileUsername = (TextView) findViewById(R.id.profile_user_name);
        fn = (TextView) findViewById(R.id.fullname);
        ph1 = (TextView) findViewById(R.id.phon1);
        ph2 = (TextView) findViewById(R.id.phon2);

        co = (TextView) findViewById(R.id.county);


        //  findViewById(R.id.launch_sign_in).setOnClickListener(this);
        // findViewById(R.id.show_feeds_button).setOnClickListener(this);
        // findViewById(R.id.sign_out_button).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseUser currentUser = mAuth.getCurrentUser();
        // DatabaseReference mref=databaseTracks.child("people").child(getUid());
        String uid = getUid();
        if (currentUser != null && !currentUser.isAnonymous()) {
            // dismissProgressDialog();

            showSignedInUI(currentUser);
            databaseTracks.child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get Post object and use the values to update the UI
                    User1 user = dataSnapshot.getValue(User1.class);
                    // [START_EXCLUDE]
                    if (user != null) {
                        fn.setText(user.getFullname());
                        ph1.setText(user.getPhone1());
                        ph2.setText(user.getPhone2());
                        co.setText(user.getHome());
                    }
                    // [END_EXCLUDE]
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                    // [START_EXCLUDE]
                    Toast.makeText(UserP.this, "Failed to load post.",
                            Toast.LENGTH_SHORT).show();
                    // [END_EXCLUDE]
                }
            });

        } else {

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onStop() {
        super.onStop();

        // Remove post value event listener
        if (mPostListener != null) {
            databaseTracks.removeEventListener(mPostListener);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGoogleSignInResult(result);
        }
    }

    private void handleGoogleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.getStatus());
        if (result.isSuccess()) {
            // Successful Google sign in, authenticate with Firebase.
            GoogleSignInAccount acct = result.getSignInAccount();
            firebaseAuthWithGoogle(acct);
        } else {
            // Unsuccessful Google Sign In, show signed-out UI
            Log.d(TAG, "Google Sign-In failed.");
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGooogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        // showProgressDialog(getString(R.string.profile_progress_message));
        mAuth.signInWithCredential(credential)
                .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult result) {
                        handleFirebaseAuthResult(result);
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //   FirebaseCrash.logcat(Log.ERROR, TAG, "auth:onFailure:" + e.getMessage());
                        handleFirebaseAuthResult(null);
                    }
                });
    }

    private void handleFirebaseAuthResult(AuthResult result) {
        // TODO: This auth callback isn't being called after orientation change. Investigate.
        //  dismissProgressDialog();
        if (result != null) {
            Log.d(TAG, "handleFirebaseAuthResult:SUCCESS");
            showSignedInUI(result.getUser());

        } else {
            Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show();
            showSignedOutUI();
        }
    }

    private void showSignedInUI(FirebaseUser firebaseUser) {
        Log.d(TAG, "Showing signed in UI");

        mProfileUi.setVisibility(View.VISIBLE);
        mProfileUsername.setVisibility(View.VISIBLE);
        mProfilePhoto.setVisibility(View.VISIBLE);
        fn.setVisibility(View.VISIBLE);
        ph1.setVisibility(View.VISIBLE);
        ph2.setVisibility(View.VISIBLE);
        co.setVisibility(View.VISIBLE);

        if (firebaseUser.getDisplayName() != null) {
            mProfileUsername.setText(firebaseUser.getDisplayName());
        }


        if (firebaseUser.getPhotoUrl() != null) {
            GlideUtil.loadProfileIcon(firebaseUser.getPhotoUrl().toString(), mProfilePhoto);
        }
        Map<String, Object> updateValues = new HashMap<>();
        updateValues.put("displayName", firebaseUser.getDisplayName() != null ? firebaseUser.getDisplayName() : "Anonymous");
        updateValues.put("photoUrl", firebaseUser.getPhotoUrl() != null ? firebaseUser.getPhotoUrl().toString() : null);

        FirebaseUtil.getPeopleRef().child(firebaseUser.getUid()).updateChildren(
                updateValues,
                new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError firebaseError, DatabaseReference databaseReference) {
                        if (firebaseError != null) {
                            Toast.makeText(UserP.this,
                                    "Couldn't save user data: " + firebaseError.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        showdialog();
    }


    private void showdialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog1, null);
        dialogBuilder.setView(dialogView);

        final EditText editfull = (EditText) dialogView.findViewById(R.id.editfull);
        final EditText editphone1 = (EditText) dialogView.findViewById(R.id.editphone1);
        final EditText editphone2 = (EditText) dialogView.findViewById(R.id.editphone2);
        final EditText homelocation = (EditText) dialogView.findViewById(R.id.homelocation);

        final Button buttonNew = (Button) dialogView.findViewById(R.id.buttonNewArtist);

        dialogBuilder.setTitle("User Information");
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = getUid();

                String fullname = editfull.getText().toString().trim();
                String phone1 = editphone1.getText().toString().trim();
                String phone2 = editphone2.getText().toString().trim();
                String home = homelocation.getText().toString().trim();
                // String username= FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                //   private void writeNewUser(String userId, String name, String email) {
                // Title is required
                if (TextUtils.isEmpty(fullname)) {
                    editfull.setError(REQUIRED);
                    return;
                }

                // Body is required
                if (TextUtils.isEmpty(phone1)) {
                    editphone1.setError(REQUIRED);
                    return;
                }
                if (TextUtils.isEmpty(phone2)) {
                    editphone2.setError(REQUIRED);
                    return;
                }

                // Body is required
                if (TextUtils.isEmpty(home)) {
                    homelocation.setError(REQUIRED);
                    return;
                }

                User1 user = new User1(fullname, phone1, phone2, home);

                //   mDatabase.child("users").child(userId).setValue(user);


                mDatabase.child("user").child(uid).setValue(user);
                Snackbar.make(findViewById(android.R.id.content), "Profile Updated", Snackbar.LENGTH_LONG)

                        .setActionTextColor(Color.BLUE)
                        .show();
                b.dismiss();

            }
        });

    }

    private void showSignedOutUI() {
        Log.d(TAG, "Showing signed out UI");
        mProfileUsername.setText("");
        mSignInUi.setVisibility(View.VISIBLE);
        mProfileUi.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.w(TAG, "onConnectionFailed:" + connectionResult);
    }

}
