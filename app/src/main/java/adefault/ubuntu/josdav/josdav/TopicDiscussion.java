package adefault.ubuntu.josdav.josdav;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TopicDiscussion extends AppCompatActivity {
    Button buttonAddTrack;
    private static final String REQUIRED = "Required";
    EditText editTextTrackName;

    TextView textViewRating, textViewArtist;
    ListView listViewTracks;
    ExpandableRelativeLayout expandableLayout27;
    DatabaseReference databaseTracks;

    List<Answers> tracks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_disc);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        /*
        * this line is important
        * this time we are not getting the reference of a direct node
        * but inside the node track we are creating a new child with the artist id
        * and inside that node we will store all the tracks with unique ids
        * */
        databaseTracks = FirebaseDatabase.getInstance().getReference("topic_comment").child(intent.getStringExtra(SendMessage.TOPIC_NAME));

        buttonAddTrack = (Button) findViewById(R.id.buttonAddTrack);
        editTextTrackName = (EditText) findViewById(R.id.editTextName);
      //  seekBarRating = (SeekBar) findViewById(R.id.seekBarRating);
       // textViewRating = (TextView) findViewById(R.id n.textViewRating);
        textViewArtist = (TextView) findViewById(R.id.textViewArtist);
        listViewTracks = (ListView) findViewById(R.id.listViewTracks);

        tracks = new ArrayList<>();

        textViewArtist.setText(intent.getStringExtra(SendMessage.TOPIC_NAME));


        buttonAddTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTrack();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseTracks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tracks.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Answers track = postSnapshot.getValue(Answers.class);
                    tracks.add(track);
                }
                AnswersList trackListAdapter = new AnswersList(TopicDiscussion.this, tracks);
                listViewTracks.setAdapter(trackListAdapter);
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
    public void expandableButton17(View view) {
        expandableLayout27 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout27);
        expandableLayout27.toggle(); // toggle expand and collapse
    }
    private void saveTrack() {
        String trackNames= editTextTrackName.getText().toString().trim();

        String username = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        if (!TextUtils.isEmpty(trackNames)) {
            String id  = databaseTracks.push().getKey();
            Answers track = new Answers(id, trackNames, username);
            databaseTracks.child(id).setValue(track);
            Snackbar.make(findViewById(android.R.id.content), "Answer Posted", Snackbar.LENGTH_LONG)

                    .setActionTextColor(Color.BLUE)
                    .show();
            //Toast.makeText(this, "Comment saved", Toast.LENGTH_LONG).show();
            editTextTrackName.setText("");
        } else {
            // Body is required
            if (TextUtils.isEmpty(trackNames)) {
                editTextTrackName.setError(REQUIRED);
                return;
            }
            //Toast.makeText(this, "Please enter a comment", Toast.LENGTH_LONG).show();
        }
    }
}
