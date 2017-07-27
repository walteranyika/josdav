package adefault.ubuntu.josdav.josdav;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import adefault.ubuntu.josdav.josdav.adapters.SalesAdapter;

/**
 * Created by android on 7/18/2016.
 */
public  class Ponds2 extends Fragment {

    private static final String TAG = "PostListFragment";

    private DatabaseReference  keysRef;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    private List<Pond> mPondList;
    SalesAdapter recyclerAdapter;

    public Ponds2() {}

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_all_ponds, container, false);
        mRecycler = (RecyclerView) rootView.findViewById(R.id.messages_list);
        mRecycler.setHasFixedSize(true);
        mPondList = new ArrayList<>();
        Log.d("KEYS_POND_SIZE", "" + mPondList.size());
        recyclerAdapter = new SalesAdapter(mPondList, getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setItemAnimator(new DefaultItemAnimator());


        keysRef = FirebaseDatabase.getInstance().getReference().child("pondData/" + getUid());

        keysRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mPondList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Log.d("KEY", item.getKey());
                    Log.d("KEY_STRING", item.toString());
                    DataSnapshot single = item.child("details");
                    Log.d("KEY_STRING_SINGLE", single.toString());
                    Pond pond = single.getValue(Pond.class);

                    mPondList.add(pond);
                }
                recyclerAdapter.notifyDataSetChanged();
                Log.d("KEYS_POND_SIZE", "" + mPondList.size());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ERROR", "Error happened while fetching");
            }
        });

        mRecycler.setAdapter(recyclerAdapter);

        return rootView;
    }




    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }



}

