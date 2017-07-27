package adefault.ubuntu.josdav.josdav;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by android on 7/16/2016.
 */
public class SalesViewHolder extends RecyclerView.ViewHolder {

   // public TextView authorView;

    public TextView price;
    public TextView size;
    public TextView number;


    public TextView genre;


    public TextView numStarsView;


    public SalesViewHolder(View itemView) {
        super(itemView);


        //authorView = (TextView) itemView.findViewById(R.id.post_author);
        price=(TextView)itemView.findViewById(R.id.price);
        size=(TextView)itemView.findViewById(R.id.size);
        number = (TextView) itemView.findViewById(R.id.number);
        genre=(TextView)itemView.findViewById(R.id.genre);
            //starView = (ImageView) itemView.findViewById(R.id.star);
        //numStarsView = (TextView) itemView.findViewById(R.id.post_num_stars);

    }

    public void bindToPost(Sales sales, View.OnClickListener starClickListener) {
       // authorView.setText(pond.author);
        price.setText(sales.price);
        number.setText(sales.number);
        size.setText(sales.size);


        genre.setText(sales.genre);


        //starView.setOnClickListener(starClickListener);
    }
}
