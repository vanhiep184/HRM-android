package eu.berdosi.app.heartbeat.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import eu.berdosi.app.heartbeat.R;

public class CustomListAdapter extends BaseAdapter {

    private final List<HistoryMeasure> listData;
    private final LayoutInflater layoutInflater;

    public CustomListAdapter(List<HistoryMeasure> listData, Context context) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_layout, null);
            holder = new ViewHolder();
            holder.flagView = (ImageView) convertView.findViewById(R.id.imageView_flag);
            holder.time = (TextView) convertView.findViewById(R.id.textView_time);
            holder.measureResult = (TextView) convertView.findViewById(R.id.textView_result);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HistoryMeasure historyMeasure = this.listData.get(position);
        holder.time.setText(formatTime(historyMeasure.getTime()));
        holder.measureResult.setText(Integer.toString(historyMeasure.getMeasureResult()));
        return convertView;
    }
    static class ViewHolder {
        ImageView flagView;
        TextView time;
        TextView measureResult;
    }

    public String formatTime(LocalDateTime time){
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy    hh:mm:ss");
        return customFormatter.format(time);
    }

}
