package adefault.ubuntu.josdav.josdav.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import adefault.ubuntu.josdav.josdav.Pond;
import adefault.ubuntu.josdav.josdav.PondDaily;
import adefault.ubuntu.josdav.josdav.R;

/**
 * Created by csa on 3/7/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyHolder> {

    List<Pond> list;
    Context context;

    public RecyclerAdapter(List<Pond> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.pond_item_post, parent, false);
        MyHolder myHoder = new MyHolder(view);


        return myHoder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        Pond item = list.get(position);
        holder.fishes.setText(item.fish);
        holder.pondid.setText(item.pond_id);
        holder.pondsize.setText(item.pond_size);
        holder.pondstock.setText(item.pond_stock);
        holder.fishsize.setText(item.fish_size);
        holder.fishtype.setText(item.fish_type);
        holder.locality.setText(item.location);
    }

    @Override
    public int getItemCount() {

        int arr = 0;

        try {
            if (list.size() == 0) {

                arr = 0;

            } else {

                arr = list.size();
            }


        } catch (Exception e) {


        }

        return arr;

    }

    class MyHolder extends RecyclerView.ViewHolder{
        public TextView fishes;
        public TextView pondid;
        public TextView pondsize;
        public TextView pondstock;
        public TextView fishsize;
        public TextView fishtype;
        public TextView locality;


        public MyHolder(View itemView) {
            super(itemView);
            fishes = (TextView) itemView.findViewById(R.id.fish);
            pondid = (TextView) itemView.findViewById(R.id.pond_id);
            pondsize = (TextView) itemView.findViewById(R.id.pond_size);
            pondstock = (TextView) itemView.findViewById(R.id.pond_stock);
            fishsize = (TextView) itemView.findViewById(R.id.fish_size);
            fishtype = (TextView) itemView.findViewById(R.id.fish_type);
            locality = (TextView) itemView.findViewById(R.id.location);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // Log.d("CLICKED","CLICKI "+getAdapterPosition());
                    int pos =getAdapterPosition();
                    Pond pond = list.get(pos);
                    String pond_id=pond.pond_id.replace(" ","_").toLowerCase();
                    Intent intent =new Intent(context, PondDaily.class);
                    intent.putExtra("POND_KEY",pond_id);
                    context.startActivity(intent);
                }
            });

        }


    }

}