package com.project.organizer.helper.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.project.organizer.model.shipper.Shipper;
import com.project.organizer.model.shipper.ShippingStatus;

import java.util.ArrayList;
import java.util.List;

public class ShippingStateSpinnerAdapter extends ArrayAdapter<ShippingStatus> {

    private int actPosition;
    private Context mContext;
    private List<ShippingStatus> shipping_status_list = new ArrayList<ShippingStatus>();


    public ShippingStateSpinnerAdapter(@NonNull Context context, int textViewResourceId, ArrayList<ShippingStatus> shipper_list) {
        super(context, textViewResourceId, shipper_list);
        mContext = context;
        this.shipping_status_list = shipper_list;
    }


    public void setPos(int actPosition) {
        this.actPosition = actPosition;
    }

    public ShippingStatus getItem() {
        return shipping_status_list.get(actPosition);
    }

    @Override
    public int getCount() {
        return shipping_status_list.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setText(shipping_status_list.get(position).getName());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    public int getPosition(int ShipperId) {
        int count = 0;
        for (ShippingStatus shipping_status: shipping_status_list) {
            if (shipping_status.getId() == ShipperId) {
                return count;
            }
            count++;
        }
        return -1;
    }
}
