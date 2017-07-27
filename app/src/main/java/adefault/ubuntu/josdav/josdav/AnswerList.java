package adefault.ubuntu.josdav.josdav;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Belal on 2/26/2017.
 */

public class AnswerList extends ArrayAdapter<Answer> {
    private Activity context;
    List<Answer> tracks;

    public AnswerList(Activity context, List<Answer> tracks) {
        super(context, R.layout.layout_artist_list, tracks);
        this.context = context;
        this.tracks = tracks;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_artist_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewRating = (TextView) listViewItem.findViewById(R.id.username);

        Answer track = tracks.get(position);
        textViewName.setText(track.getTrackName());
        textViewRating.setText(track.getUsername());

        return listViewItem;
    }
}