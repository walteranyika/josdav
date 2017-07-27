package adefault.ubuntu.josdav.josdav;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by android on 6/29/2017.
 */

public  class Topiclist extends ArrayAdapter<TOPIC> {
        private Activity context;
        List<TOPIC> artists;

        public Topiclist(Activity context, List<TOPIC> artists) {
                super(context, R.layout.layout_artist_list, artists);
                this.context = context;
                this.artists = artists;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = context.getLayoutInflater();
                View listViewItem = inflater.inflate(R.layout.layout_artist_list, null, true);

                TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
                TextView textViewGenre = (TextView) listViewItem.findViewById(R.id.username);

                TOPIC artist = artists.get(position);
                textViewName.setText(artist.getArtistName());
                textViewGenre.setText(artist.getUsername());

                return listViewItem;
        }
}