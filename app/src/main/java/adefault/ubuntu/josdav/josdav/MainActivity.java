package adefault.ubuntu.josdav.josdav;

import android.app.Dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class MainActivity  extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    private static final String REQUIRED = "Required";
    // Declaring Your View and Variables
    private Switch mySwitch;
    Toolbar toolbar;
    ViewPager view_pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Data","Sales","Forum"};
    int Numboftabs =3;
    final Context context = this;

    String email, subject, message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                //LOGIC CHECK USER
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user == null) {

                    finish();
                    Intent moveToHome = new Intent(MainActivity.this, GoogleSignInActivity.class);
                    moveToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(moveToHome);
                }
            }
        };


        // toolbar = (Toolbar) findViewById(R.id.tool_bar);
//        setSupportActionBar(toolbar);
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);

        // Assigning ViewPager View and setting the adapter
        view_pager = (ViewPager) findViewById(R.id.view_pager);
        view_pager.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Write here anything that you wish to do on click of FAB
                // Code to Add an item with default animation
                startActivity(new Intent(getApplicationContext(), Newpond.class));
                //Ends Here
            }
        });
        FloatingActionButton feed = (FloatingActionButton) findViewById(R.id.feed);
        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom);
                dialog.setTitle("Give Your Feedback");

                // set the custom dialog components - text, image and button

                final EditText editText = (EditText) dialog.findViewById(R.id.one);
                final EditText editText1 = (EditText) dialog.findViewById(R.id.two);
                final EditText editText2 = (EditText) dialog.findViewById(R.id.three);


                Button dialogButton = (Button) dialog.findViewById(R.id.sendit);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty( email)) {
                            editText.setError(REQUIRED);
                            return;
                        }
                        if (TextUtils.isEmpty(subject)) {
                            editText1.setError(REQUIRED);
                            return;
                        }

                        // Body is required
                        if (TextUtils.isEmpty(message)) {
                            editText2.setError(REQUIRED);
                            return;
                        }
                        email = editText.getText().toString();
                        subject = editText1.getText().toString();
                        message = editText2.getText().toString();
                        // Body is required


                        String[] TO = {"williampius17@gmail.com"};
                        String[] CC = {"fortunegacaga@gmail.com"};

                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setData(Uri.parse("mailto:"));
                        emailIntent.setType("text/plain");
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                        emailIntent.putExtra(Intent.EXTRA_BCC, CC);
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                        emailIntent.putExtra(Intent.EXTRA_TEXT, message);
                        try {
                            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                            finish();
                            Log.i("Feedback Sent...", "");
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(MainActivity.this,
                                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }


                });
                dialog.show();
            }


        });

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(view_pager);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logo: {

                Intent feedsIntent = new Intent(this, GoogleSignInActivity.class);
                startActivity(feedsIntent);
            }

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

