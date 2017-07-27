package adefault.ubuntu.josdav.josdav;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class FCRIN extends AppCompatActivity {

    public static final String EXTRA_POST_KEY = "post_key";
    private String mPostKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcrin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPostKey = getIntent().getStringExtra(EXTRA_POST_KEY);
        if (mPostKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_POST_KEY");
        }

    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
