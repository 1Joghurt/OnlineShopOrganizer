package com.project.organizer.helper.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.project.organizer.model.shipper.ShippingStatus;
import com.project.organizer.model.trader.OrderStatus;

import java.util.ArrayList;
import java.util.List;

public class OrderStateSpinnerAdapter extends ArrayAdapter<OrderStatus> {

    private int actPosition;
    private Context mContext;
    private List<OrderStatus> order_status_list = new ArrayList<OrderStatus>();


    public OrderStateSpinnerAdapter(@NonNull Context context, int textViewResourceId, ArrayList<OrderStatus> shipper_list) {
        super(context, textViewResourceId, shipper_list);
        mContext = context;
        this.order_status_list = shipper_list;
    }


    public void setPos(int actPosition) {
        this.actPosition = actPosition;
    }

    public OrderStatus getItem() {
        return order_status_list.get(actPosition);
    }

    @Override
    public int getCount() {
        return order_status_list.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setText(order_status_list.get(position).getName());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    public int getPosition(int orderStateId) {
        int count = 0;
        for (OrderStatus orderStatus: order_status_list) {
            if (orderStatus.getId() == orderStateId) {
                return count;
            }
            count++;
        }
        return -1;
    }
}
