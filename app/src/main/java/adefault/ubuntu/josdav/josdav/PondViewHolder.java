package adefault.ubuntu.josdav.josdav;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by android on 7/16/2016.
 */
public class PondViewHolder extends RecyclerView.ViewHolder {

    // public TextView authorView;
    public TextView fishes;
    public TextView pondid;
    public TextView pondsize;
    public TextView pondstock;
    public TextView fishsize;
    public TextView fishtype;
    public TextView locality;

    public TextView numStarsView;

    public PondViewHolder(View itemView) {
        super(itemView);


        //  authorView = (TextView) itemView.findViewById(R.id.post_author);
        fishes = (TextView) itemView.findViewById(R.id.fish);
        pondid = (TextView) itemView.findViewById(R.id.pond_id);
        pondsize = (TextView) itemView.findViewById(R.id.pond_size);
        pondstock = (TextView) itemView.findViewById(R.id.pond_stock);
        fishsize = (TextView) itemView.findViewById(R.id.fish_size);
        fishtype = (TextView) itemView.findViewById(R.id.fish_type);
        locality = (TextView) itemView.findViewById(R.id.location);

        //starView = (ImageView) itemView.findViewById(R.id.star);
        //numStarsView = (TextView) itemView.findViewById(R.id.post_num_stars);

    }

    public void bindToPost(Pond pond, View.OnClickListener starClickListener) {

        fishes.setText(pond.fish);
        pondid.setText(pond.pond_id);
        pondsize.setText(pond.pond_size);
        pondstock.setText(pond.pond_stock);
        fishsize.setText(pond.fish_size);
        fishtype.setText(pond.fish_type);
        locality.setText(pond.location);


    }
}
