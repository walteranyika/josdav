package adefault.ubuntu.josdav.josdav;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class SendMessage   extends BaseActivity {
    public static final String ARTIST_ID = "";
    public static final String TOPIC_NAME = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
    private static final String REQUIRED = "Required";
    EditText editTextName;

    Button buttonAddArtist;
    ListView listViewArtists;

    //a list to store all the artist from firebase database
    List<TOPIC> topics;

    //our database reference object
    DatabaseReference databaseTopics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //getting the reference of artists node
        databaseTopics = FirebaseDatabase.getInstance().getReference("topics");

        //getting views
        editTextName = (EditText) findViewById(R.id.editTextName);

        listViewArtists = (ListView) findViewById(R.id.listViewArtists);

        //buttonAddArtist = (Button) findViewById(R.id.buttonAddArtist);

        //list to store artists
        topics = new ArrayList<>();

        Button fb = (Button) findViewById(R.id.fab);


        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addTopic();

            }
        });
        //attaching listener to listview
        listViewArtists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getting the selected artist
                TOPIC artist = topics.get(i);

                //creating an intent
                Intent intent = new Intent(getApplicationContext(), TopicDiscussion.class);

                //putting artist name and id to intent
                intent.putExtra(ARTIST_ID, artist.getUid());
                intent.putExtra(TOPIC_NAME, artist.getArtistName());

                //starting the activity with intent
                startActivity(intent);
            }
        });

        listViewArtists.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                TOPIC topic = topics.get(i);
                showUpdateDeleteDialog(topic.getUid(), topic.getArtistName());
                return true;
            }
        });


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
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("topics").child(uid);

        //updating artist
        TOPIC topic = new TOPIC(uid, name,username);
        dR.setValue(topic);
       // Toast.makeText(getApplicationContext(), "Topic Updated", Toast.LENGTH_LONG).show();
        Snackbar.make(findViewById(android.R.id.content), "Topic Updated", Snackbar.LENGTH_LONG)

                .setActionTextColor(Color.BLUE)
                .show();
        return true;
    }

    private boolean deleteArtist(String id) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("topics").child(id);

        //removing artist
        dR.removeValue();

        //getting the tracks reference for the specified artist
        DatabaseReference drTracks = FirebaseDatabase.getInstance().getReference("topics").child(id);

        //removing all tracks
        drTracks.removeValue();
       // Toast.makeText(getApplicationContext(), "Topic Deleted", Toast.LENGTH_LONG).show();
        Snackbar.make(findViewById(android.R.id.content), "Topic Deleted", Snackbar.LENGTH_LONG)

                .setActionTextColor(Color.BLUE)
                .show();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseTopics.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                topics.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    TOPIC artist = postSnapshot.getValue(TOPIC.class);
                    //adding artist to the list
                    topics.add(artist);
                }

                //creating adapter
                Topiclist artistAdapter = new Topiclist(SendMessage.this, topics);
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



    private void addTopic() {
        //getting the values to save
        String name = editTextName.getText().toString().trim();
        String username= FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        //checking if the value is provided
        if (!TextUtils.isEmpty(name)) {
            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String uid = databaseTopics.push().getKey();

            //creating an Artist Object
            TOPIC topic = new TOPIC(uid, name,username);

            //Saving the Artist
            databaseTopics.child(uid).setValue(topic);

            //setting edittext to blank again
            editTextName.setText("");

            //displaying a success toast
            Snackbar.make(findViewById(android.R.id.content), "Topic Posted", Snackbar.LENGTH_LONG)

                    .setActionTextColor(Color.BLUE)
                    .show();
           // Toast.makeText(this, "Topic added", Toast.LENGTH_LONG).show();
        } else {


            // Body is required
            if (TextUtils.isEmpty(name)) {
                editTextName.setError(REQUIRED);
                return;
            }           //
            // Toast.makeText(this, "Please enter a topic", Toast.LENGTH_LONG).show();
        }
    }
}
