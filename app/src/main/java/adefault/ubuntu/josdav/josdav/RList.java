package adefault.ubuntu.josdav.josdav;

import android.app.Activity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.firebase.client.core.Repo;

import java.util.List;

/**
 * Created by android on 7/5/2017.
 */

public class RList extends ArrayAdapter<Report> {
    private Activity context;
    List<Report> data;

    public RList(Activity context, List<Report> data) {
        super(context, R.layout.layout_user_list, data);
        this.context = context;
        this.data = data;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_user_list1, null, true);

        TextView textViewfull = (TextView) listViewItem.findViewById(R.id.fullname);
        TextView textViewp1 = (TextView) listViewItem.findViewById(R.id.pho1);
        TextView textViewp2 = (TextView) listViewItem.findViewById(R.id.messageTime);
        //TextView textViewhome= (TextView) listViewItem.findViewById(R.id.homelo);


        Report report = data.get(position);
        textViewfull.setText(report.getFeed_quantity());
        textViewp1.setText(report.getFeed_type());
       textViewp2.setText((DateFormat.format("dd-MM-yyyy (HH:mm:ss)", report.getMessageTime())));
       // textViewhome.setText(track.getHome());

        return listViewItem;
    }
}