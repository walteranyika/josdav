package adefault.ubuntu.josdav.josdav;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import adefault.ubuntu.josdav.josdav.models.FishGrowth;
import static adefault.ubuntu.josdav.josdav.PondDaily.EXTRA_POST_KEY;

public class FishGrowthActivity extends AppCompatActivity {
    EditText edtFishSize;
    TextView tvPondName;
    String pondName="";
    private DatabaseReference pondRefrence;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish_growth);
        getSupportActionBar().setTitle("Fish Growth Data");
        edtFishSize= (EditText) findViewById(R.id.edtFishWeight);
        tvPondName= (TextView) findViewById(R.id.tvPondName);
        pondName = getIntent().getStringExtra(EXTRA_POST_KEY);
        tvPondName.setText(pondName.replace("_"," ").toUpperCase());
    }

    public void submit(View view) {
       String qty_str=edtFishSize.getText().toString().trim();
       if (qty_str.isEmpty()){
           Snackbar.make(edtFishSize,"Required.Fish Size",Snackbar.LENGTH_SHORT).show();
           return;
       }
       double size = Double.parseDouble(qty_str);
        //upload
        FishGrowth fishGrowth=new FishGrowth(size,pondName.replace("_"," ").toUpperCase());
        pondRefrence = FirebaseDatabase.getInstance().getReference().child("pondData/"+getUid()+"/"+pondName+"/fishGrowth/"+fishGrowth.getTime());
        pondRefrence.setValue(fishGrowth).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Snackbar.make(findViewById(android.R.id.content), "Info Added Successfully", Snackbar.LENGTH_LONG).setActionTextColor(Color.BLUE).show();
                edtFishSize.setText("");
            }
        });

    }
    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
