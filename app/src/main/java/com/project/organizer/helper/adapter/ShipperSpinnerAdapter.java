package com.project.organizer.helper.adapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.project.organizer.model.shipper.Shipper;
import com.project.organizer.model.trader.Trader;

import java.util.ArrayList;
import java.util.List;

public class ShipperSpinnerAdapter extends ArrayAdapter<Shipper> {

    private int actPosition;
    private Context mContext;
    private List<Shipper> shipper_list = new ArrayList<Shipper>();


    public ShipperSpinnerAdapter(@NonNull Context context, int textViewResourceId, ArrayList<Shipper> shipper_list) {
        super(context, textViewResourceId, shipper_list);
        mContext = context;
        this.shipper_list = shipper_list;
    }


    public void setPos(int actPosition) {
        this.actPosition = actPosition;
    }

    public Shipper getItem() {
        return shipper_list.get(actPosition);
    }

    @Override
    public int getCount() {
        return shipper_list.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setText(shipper_list.get(position).getName());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    public int getPosition(int ShipperId) {
        int count = 0;
        for (Shipper shipper : shipper_list) {
            if (shipper.getShipperId() == ShipperId) {
                return count;
            }
            count++;
        }
        return -1;
    }
}

