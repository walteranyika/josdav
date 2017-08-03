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
import adefault.ubuntu.josdav.josdav.models.MonthlySale;
import adefault.ubuntu.josdav.josdav.models.Sales;

public class SalesListAdapter extends BaseAdapter {


    private Context mContext;
    private ArrayList<MonthlySale> temporaryArray;
    private ArrayList<MonthlySale> permanentArray;

    public SalesListAdapter(Context context, ArrayList<MonthlySale> data) {
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
            convertView = inflater.inflate(R.layout.sales_item_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvMonth = (TextView) convertView.findViewById(R.id.saleMonth);
            viewHolder.tvPond = (TextView) convertView.findViewById(R.id.salePond);
            viewHolder.tvTotal = (TextView) convertView.findViewById(R.id.saleTotal);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final MonthlySale item = temporaryArray.get(position);
        viewHolder.tvMonth.setText(item.getMonth());
        viewHolder.tvPond.setText(item.getPondName());
        viewHolder.tvTotal.setText(item.getTotal()+"");
        return convertView;
    }

    public void filter(String text) {
     /* text = text.toLowerCase();
        temporaryArray.clear();
        if (text.trim().length() == 0) {
            temporaryArray.addAll(permanentArray);
        } else {
            for (Comment p : permanentArray) {
                //|| (p.getCode()+"").contains(text) || (p.getPrice()+"").contains(text)
                if (p.getFeed_type().toLowerCase().contains(text) || (p.getPondId() + "").contains(text) || p.getFormattedDate(p.messageTime).contains(text) ) {
                    temporaryArray.add(p);
                }
            }
            Log.d("SEARCH", "COUNT " + temporaryArray.size());
        }
        notifyDataSetChanged();*/
    }

    static class ViewHolder {
        TextView tvMonth;
        TextView tvPond;
        TextView tvTotal;
    }
}

