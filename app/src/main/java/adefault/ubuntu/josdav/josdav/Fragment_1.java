package adefault.ubuntu.josdav.josdav;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


/**
 * Created by android on 6/15/2017.
 */

public class Fragment_1 extends Fragment {
    Intent intent;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_1, container, false);
        final LinearLayout mRelativeLayout = (LinearLayout) inflater.inflate(R.layout.buttons,
                container, false);

        Button mBu = (Button) mRelativeLayout.findViewById(R.id.buttonu);
        mBu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // here you set what you want to do when user clicks your button,
                  Intent intent = new Intent(getActivity(), UserP.class);
                startActivity(intent);
            }
        });


        Button butto = (Button) mRelativeLayout.findViewById(R.id.reps);
        butto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // here you set what you want to do when user clicks your button,
                Intent intent = new Intent(getActivity(), Reports.class);
                startActivity(intent);
            }
        });

        return mRelativeLayout;




    }
    }
