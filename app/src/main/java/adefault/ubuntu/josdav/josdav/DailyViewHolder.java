package adefault.ubuntu.josdav.josdav;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import adefault.ubuntu.josdav.josdav.models.Comment;

/**
 * Created by android on 7/16/2016.
 */
public class DailyViewHolder extends RecyclerView.ViewHolder {

   // public TextView authorView;

    public TextView feed;
    public TextView feedquant;
    public TextView mortali;
    public TextView temp;

    public TextView genre;

public  TextView mood;
    public TextView numStarsView;
   // public TextView messageTime;


    public DailyViewHolder(View itemView) {
        super(itemView);


       // messageTime = (TextView) itemView.findViewById(R.id.message_time);
        feed = (TextView) itemView.findViewById(R.id.feed_type);
        feedquant = (TextView) itemView.findViewById(R.id.feed_quantity);
        mortali = (TextView) itemView.findViewById(R.id.mortality);
        temp = (TextView) itemView.findViewById(R.id.temperature);
        genre = (TextView) itemView.findViewById(R.id.genre);
              mood = (TextView) itemView.findViewById(R.id.mood);
        //starView = (ImageView) itemView.findViewById(R.id.star);
        //numStarsView = (TextView) itemView.findViewById(R.id.post_num_stars);
    }

    public void bindToPost(Comment daily, View.OnClickListener starClickListener) {
       // authorView.setText(pond.author);
        feed.setText(daily.feed_type);
        feedquant.setText(daily.feed_quantity);
        mortali.setText(daily.mortality);
        temp.setText(daily.temperature);
          genre.setText(daily.genre);
mood.setText(daily.mood);
//messageTime.setText(daily.message_time);
        //starView.setOnClickListener(starClickListener);
    }
}
