package adefault.ubuntu.josdav.josdav;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by android on 7/16/2016.
 */
public class CageViewHolder extends RecyclerView.ViewHolder {

   // public TextView authorView;
    public  TextView cageid;
    public TextView cagesize;
    public TextView cagestock;
    public TextView fishsize;
    public TextView fishtype;
    public TextView locality;

    public TextView numStarsView;

    public CageViewHolder(View itemView) {
        super(itemView);


       // authorView = (TextView) itemView.findViewById(R.id.post_author);
        cageid=(TextView)itemView.findViewById(R.id.cage_id);
        cagesize = (TextView) itemView.findViewById(R.id.cage_size);
        cagestock = (TextView) itemView.findViewById(R.id.cage_stock);
        fishsize = (TextView) itemView.findViewById(R.id.fish_size);
       fishtype = (TextView) itemView.findViewById(R.id.fish_type);
        locality = (TextView) itemView.findViewById(R.id.location);

        //starView = (ImageView) itemView.findViewById(R.id.star);
        //numStarsView = (TextView) itemView.findViewById(R.id.post_num_stars);

    }

    public void bindToPost(Cage cage, View.OnClickListener starClickListener) {
       // authorView.setText(cage.author);
        cageid.setText(cage.cage_id);
        cagesize.setText(cage.cage_size);
       cagestock.setText(cage.cage_stock);
        fishsize.setText(cage.fish_size);
        fishtype.setText(cage.fish_type);
       locality.setText(cage.location);


        //starView.setOnClickListener(starClickListener);
    }
}
