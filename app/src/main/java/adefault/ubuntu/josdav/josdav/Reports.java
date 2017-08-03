package adefault.ubuntu.josdav.josdav;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Reports extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

    }

    public void dailyReports(View view) {
        startActivity(new Intent(this, DailyReportsActivity.class));
    }

    public void monthlyReports(View view) {
        //startActivity(new Intent(this, MonthlyReportsActivity.class));
        startActivity(new Intent(this, MonthlySalesReportActivity.class));
    }

    public void fishGrowth(View view) {
        startActivity(new Intent(this, FishGrowthReportActivity.class));
    }
}
