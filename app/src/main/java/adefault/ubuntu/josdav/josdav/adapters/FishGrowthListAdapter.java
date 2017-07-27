package adefault.ubuntu.josdav.josdav.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import adefault.ubuntu.josdav.josdav.R;
import adefault.ubuntu.josdav.josdav.models.FishGrowth;

public class FishGrowthListAdapter extends BaseAdapter {


    private Context mContext;
    private ArrayList<FishGrowth> temporaryArray;
    private ArrayList<FishGrowth> permanentArray;

    public FishGrowthListAdapter(Context context, ArrayList<FishGrowth> data) {
        this.mContext = context;
        this.temporaryArray = data;
        this.permanentArray = new ArrayList<>();
        this.permanentArray.addAll(data);
    }

    @Override
    public int getCount() {
        return temporaryArray.size();// # of items in your arraylist
    }

    @Override
    public Object getItem(int position) {
        return temporaryArray.get(position);// get the actual movie
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.fish_growth_item_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvDay = (TextView) convertView.findViewById(R.id.reportDate);
            viewHolder.tvFishSize = (TextView) convertView.findViewById(R.id.reportFishSize);
            viewHolder.tvPond = (TextView) convertView.findViewById(R.id.reportPond);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final FishGrowth product = temporaryArray.get(position);
        viewHolder.tvDay.setText(product.getDate());
        viewHolder.tvFishSize.setText(""+product.getSize());
        viewHolder.tvPond.setText(product.getFishPond());
        return convertView;
    }

    public void filter(String text) {
        text = text.toLowerCase();
        temporaryArray.clear();
        if (text.trim().length() == 0) {
            temporaryArray.addAll(permanentArray);
        }
        else
        {
            for (FishGrowth p : permanentArray)
            {
                if (p.getMonth().toLowerCase().contains(text) || (p.getSize()+ "").contains(text) || p.getFishPond().toLowerCase().contains(text) ) {
                    temporaryArray.add(p);
                }
            }
            Log.d("SEARCH", "COUNT " + temporaryArray.size());
        }
        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView tvDay;
        TextView tvPond;
        TextView tvFishSize;


    }
}

