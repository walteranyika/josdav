package adefault.ubuntu.josdav.josdav;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by android on 7/5/2017.
 */

public class UserList extends ArrayAdapter<User1> {
    private Activity context;
    List<User1> tracks;

    public UserList(Activity context, List<User1> tracks) {
        super(context, R.layout.layout_user_list, tracks);
        this.context = context;
        this.tracks = tracks;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_user_list, null, true);

        TextView textViewfull = (TextView) listViewItem.findViewById(R.id.fullname);
        TextView textViewp1 = (TextView) listViewItem.findViewById(R.id.pho1);
        TextView textViewp2 = (TextView) listViewItem.findViewById(R.id.pho2);
        TextView textViewhome= (TextView) listViewItem.findViewById(R.id.homelo);


        User1 track = tracks.get(position);
        textViewfull.setText(track.getFullname());
        textViewp1.setText(track.getPhone1());
        textViewp2.setText(track.getPhone2());
        textViewhome.setText(track.getHome());

        return listViewItem;
    }
}