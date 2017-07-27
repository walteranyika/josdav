package adefault.ubuntu.josdav.josdav;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Faqs extends AppCompatActivity {
    public static final String ARTIST_ID = "";
    public static final String ARTIST_NAME = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
    ExpandableRelativeLayout expandableLayout29;
    private static final String REQUIRED = "Required";

    EditText editTextName;

    Button buttonAddArtist;
    ListView listViewArtists;

    //a list to store all the artist from firebase database
    List<FAQ> artists;

    //our database reference object
    DatabaseReference databaseArtists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //getting the reference of artists node
        databaseArtists = FirebaseDatabase.getInstance().getReference("faqs");

        //getting views
        editTextName = (EditText) findViewById(R.id.editTextName);

        listViewArtists = (ListView) findViewById(R.id.listViewArtists);

        buttonAddArtist = (Button) findViewById(R.id.buttonAddArtist);

        //list to store artists
        artists = new ArrayList<>();


        //adding an onclicklistener to button
        buttonAddArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling the method addArtist()
                //the method is defined below
                //this method is actually performing the write operation
                addArtist();
            }
        });

        //attaching listener to listview
        listViewArtists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getting the selected artist
                FAQ artist = artists.get(i);

                //creating an intent
                Intent intent = new Intent(getApplicationContext(), FAQActivity.class);

                //putting artist name and id to intent
                intent.putExtra(ARTIST_ID, artist.getUid());
                intent.putExtra(ARTIST_NAME, artist.getArtistName());

                //starting the activity with intent
                startActivity(intent);
            }
        });

        listViewArtists.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                FAQ artist = artists.get(i);
                showUpdateDeleteDialog(artist.getUid(), artist.getArtistName());
                return true;
            }
        });


    }
    public void expandableButton18(View view) {
        expandableLayout29 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout29);
        expandableLayout29.toggle(); // toggle expand and collapse
    }
    private void showUpdateDeleteDialog(final String uid, String artistName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateArtist);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteArtist);

        dialogBuilder.setTitle(artistName);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String username=FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

                if (!TextUtils.isEmpty(name)) {
                    updateArtist(uid, name,username);
                    b.dismiss();
                }
            }
        });


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteArtist(uid);
                b.dismiss();
            }
        });
    }

    private boolean updateArtist(String uid, String name,String username) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("faqs").child(uid);

        //updating artist
        FAQ artist = new FAQ(uid, name,username);
        dR.setValue(artist);
        Snackbar.make(findViewById(android.R.id.content), "FAQ Updated", Snackbar.LENGTH_LONG)

                .setActionTextColor(Color.BLUE)
                .show();

       // Toast.makeText(getApplicationContext(), "Artist Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean deleteArtist(String id) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("faqs").child(id);

        //removing artist
        dR.removeValue();

        //getting the tracks reference for the specified artist
        DatabaseReference drTracks = FirebaseDatabase.getInstance().getReference("tracks").child(id);

        //removing all tracks
        drTracks.removeValue();
       // Toast.makeText(getApplicationContext(), "faq Deleted", Toast.LENGTH_LONG).show();
        Snackbar.make(findViewById(android.R.id.content), "FAQ Deleted", Snackbar.LENGTH_LONG)

                .setActionTextColor(Color.BLUE)
                .show();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                artists.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    FAQ artist = postSnapshot.getValue(FAQ.class);
                    //adding artist to the list

                    artists.add(artist);
                }

                //creating adapter
                FAQList artistAdapter = new FAQList(Faqs.this, artists);
                //attaching adapter to the listview
                listViewArtists.setAdapter(artistAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    /*
    * This method is saving a new artist to the
    * Firebase Realtime Database
    * */
    private void addArtist() {
        //getting the values to save
        String name = editTextName.getText().toString().trim();
       String username= FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        //checking if the value is provided
        if (!TextUtils.isEmpty(name)) {
            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String uid = databaseArtists.push().getKey();

            //creating an Artist Object
            FAQ artist = new FAQ(uid, name,username);

            //Saving the Artist
            databaseArtists.child(uid).setValue(artist);
            Snackbar.make(findViewById(android.R.id.content), "FAQ Posted", Snackbar.LENGTH_LONG)

                    .setActionTextColor(Color.BLUE)
                    .show();
            //setting edittext to blank again
            editTextName.setText("");

            //displaying a success toast
            //Toast.makeText(this, "Artist added", Toast.LENGTH_LONG).show();

        } else {
            //if the value is not given displaying a toast
            if (TextUtils.isEmpty(name)) {
                editTextName.setError(REQUIRED);
                return;
            }

            // Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }

    }
}
