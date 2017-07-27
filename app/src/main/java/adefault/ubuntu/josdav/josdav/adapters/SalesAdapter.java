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
import adefault.ubuntu.josdav.josdav.SalesData;

/**
 * Created by csa on 3/7/2017.
 */

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.MyHolder> {

    List<Pond> list;
    Context context;

    public SalesAdapter(List<Pond> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.sales_list_item_layout, parent, false);
        MyHolder myHoder = new MyHolder(view);


        return myHoder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        Pond item = list.get(position);
        holder.tvPondName.setText(item.pond_id.replace("_"," ").toUpperCase());
        holder.tvPondStock.setText(item.pond_stock);
        holder.tvPondFishType.setText(item.fish_type);
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
        public TextView tvPondName;
        public TextView tvPondStock;
        public TextView tvPondFishType;



        public MyHolder(View itemView) {
            super(itemView);
            tvPondName = (TextView) itemView.findViewById(R.id.pondName);
            tvPondStock = (TextView) itemView.findViewById(R.id.pondStock);
            tvPondFishType = (TextView) itemView.findViewById(R.id.pondFishType);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos =getAdapterPosition();
                    Pond pond = list.get(pos);
                    String pond_id=pond.pond_id.replace(" ","_").toLowerCase();
                    Intent intent =new Intent(context, SalesData.class);
                    intent.putExtra("POND_KEY",pond_id);
                    context.startActivity(intent);
                }
            });

        }


    }

}