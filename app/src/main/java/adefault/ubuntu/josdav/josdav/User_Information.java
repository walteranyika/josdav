package adefault.ubuntu.josdav.josdav;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class User_Information extends BaseActivity implements View.OnClickListener {

    ImageButton buttn;
    Button buttoni;
    private DatabaseReference mDatabase;
    ListView listViewTra;
    DatabaseReference databaseTracks;

    List<User1> tracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__information);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        databaseTracks = FirebaseDatabase.getInstance().getReference("user");

        buttoni = (Button) findViewById(R.id.dialog);
        buttoni.setOnClickListener(this);
        // buttn.setOnClickListener(this);
        listViewTra = (ListView) findViewById(R.id.listViewusers);


        tracks = new ArrayList<>();
    }


    @Override
    protected void onStart() {
        super.onStart();

        databaseTracks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tracks.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    User1 track = postSnapshot.getValue(User1.class);
                    tracks.add(track);
                }
                UserList trackListAdapter = new UserList(User_Information.this, tracks);
                listViewTra.setAdapter(trackListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        listViewTra.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getting the selected artist
                User1 artist = tracks.get(i);
               send( artist.getPhone1());
            }
        });

        listViewTra.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                User1 artist = tracks.get(i);
                call( artist.getPhone1());
                return true;
            }
        });

    }

    private void call(String phone1) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phone1));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(callIntent);
    }
    public void send( String phone1) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("smsto:"+phone1)); // This ensures only SMS apps respond

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
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
                String uid=getUid();

                String fullname = editfull.getText().toString().trim();
                String phone1 = editphone1.getText().toString().trim();
                String phone2 = editphone2.getText().toString().trim();
                String home = homelocation.getText().toString().trim();
               // String username= FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
             //   private void writeNewUser(String userId, String name, String email) {

                 User1 user = new User1(fullname,phone1,phone2,home);

                 //   mDatabase.child("users").child(userId).setValue(user);


                     mDatabase.child("user").child(uid).setValue(user);
                    b.dismiss();

            }
        });



    }


}