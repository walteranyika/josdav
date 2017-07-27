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

import adefault.ubuntu.josdav.josdav.Comment;
import adefault.ubuntu.josdav.josdav.R;
import adefault.ubuntu.josdav.josdav.models.MonthlyItem;

public class MonthlyFeedsListAdapter extends BaseAdapter {


    private Context mContext;
    private ArrayList<MonthlyItem> temporaryArray;
    private ArrayList<MonthlyItem> permanentArray;

    public MonthlyFeedsListAdapter(Context context, ArrayList<MonthlyItem> data) {
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
            convertView = inflater.inflate(R.layout.daily_report_item_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvDay = (TextView) convertView.findViewById(R.id.reportDate);
            viewHolder.tvFeedType = (TextView) convertView.findViewById(R.id.reportFeedType);
            viewHolder.tvPond = (TextView) convertView.findViewById(R.id.reportPond);
            viewHolder.tvFeedQty = (TextView) convertView.findViewById(R.id.reportQuantity);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final MonthlyItem product = temporaryArray.get(position);
        viewHolder.tvDay.setText(product.getMonth());
        viewHolder.tvFeedType.setText(product.getFeedType());
        viewHolder.tvPond.setText(product.getPondName());
        viewHolder.tvFeedQty.setText(""+product.getQuantity());





        return convertView;
    }

    public void filter(String text) {
        text = text.toLowerCase();
        temporaryArray.clear();
        if (text.trim().length() == 0) {
            temporaryArray.addAll(permanentArray);
        } else {
            for (MonthlyItem p : permanentArray) {
                //|| (p.getCode()+"").contains(text) || (p.getPrice()+"").contains(text)
              /*  if (p.getTitle().toLowerCase().contains(text) || (p.getCode() + "").contains(text) || (p.getPrice() + "").contains(text) || p.getCategory().toLowerCase().contains(text) || p.getDescription().toLowerCase().contains(text)) {
                    temporaryArray.add(p);
                }*/
            }
            Log.d("SEARCH", "COUNT " + temporaryArray.size());
        }
        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView tvDay;
        TextView tvPond;
        TextView tvFeedType;
        TextView tvFeedQty;

    }
}

