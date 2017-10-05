package adefault.ubuntu.josdav.josdav.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import adefault.ubuntu.josdav.josdav.R;
import adefault.ubuntu.josdav.josdav.models.FishGrowthSummary;

public class FishGrowthListAdapter extends BaseAdapter {


    private Context mContext;
    private ArrayList<FishGrowthSummary> temporaryArray;
    private ArrayList<FishGrowthSummary> permanentArray;

    public FishGrowthListAdapter(Context context, ArrayList<FishGrowthSummary> data) {
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
            viewHolder.tvPrevWeight = (TextView) convertView.findViewById(R.id.tvReportPondPrevWeight);
            viewHolder.tvFeedQty = (TextView) convertView.findViewById(R.id.tvReportPondFeedQty);
            viewHolder.tvPond = (TextView) convertView.findViewById(R.id.tvReportPondName);

            viewHolder.tvFCR = (TextView) convertView.findViewById(R.id.tvReportPondFcr);
            viewHolder.tvADG = (TextView) convertView.findViewById(R.id.tvReportPondADG);
            viewHolder.tvCurrWeight= (TextView) convertView.findViewById(R.id.tvReportPondCurrWeight);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final FishGrowthSummary product = temporaryArray.get(position);

        viewHolder.tvFeedQty.setText(""+String.format("%.2f", product.getFeedQty()));
        viewHolder.tvCurrWeight.setText(""+product.getCurrentWeight());
        viewHolder.tvPrevWeight.setText(""+product.getPrevWeight());
        viewHolder.tvADG.setText(""+String.format("%.2f", product.getAdg()));
        viewHolder.tvFCR.setText(""+String.format("%.2f", product.getFcr()));
        viewHolder.tvPond.setText(product.getPondName());

        return convertView;
    }

    public void filter(String text) {
       /* text = text.toLowerCase();
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
        notifyDataSetChanged();*/
    }

    static class ViewHolder {
        TextView tvPrevWeight;
        TextView tvCurrWeight;
        TextView tvFCR;
        TextView tvADG;
        TextView tvPond;
        TextView tvFeedQty;
    }
}

