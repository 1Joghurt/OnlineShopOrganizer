package com.project.organizer.helper.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.organizer.R;
import com.project.organizer.model.trader.OrderStatus;
import com.project.organizer.model.trader.Trader;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class TraderListViewAdapter extends BaseAdapter {
    Context context;
    ArrayList<OrderStatus> status_list = new ArrayList<OrderStatus>();
    LayoutInflater inflater;
    int position = -1;

    public TraderListViewAdapter(Context context ,ArrayList<OrderStatus> status_list) {
        this.context = context;
        this.status_list = status_list;
        this.inflater = (LayoutInflater.from(context));
    }

    public ArrayList<OrderStatus> getList()
    {
        return status_list;
    }

    public void addList(OrderStatus status)
    {
        status_list.add(status);
        notifyDataSetChanged();
    }

    public void removeList() {
        if (this.position != -1) {
            status_list.remove(position);
            this.position = -1;
            notifyDataSetChanged();
        }
    }

    public void setPos(int position)  {
            this.position = position;
    }

    @Override
    public int getCount() {
        return status_list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.listview_layout, null);
        TextView tv_name = (TextView) view.findViewById(R.id.lvi_name);

        if (status_list.get(position).getIsClosed()) { tv_name.setText(status_list.get(position).getName() + " (" + context.getString(R.string.yes) + ")"); }
        else { tv_name.setText(status_list.get(position).getName() + " (" + context.getString(R.string.no) + ")");}

        return view;
    }
}