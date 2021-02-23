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

public class TraderSpinnerAdapter extends ArrayAdapter<Trader> {


    private int actPosition;

    private Context mContext;
    private List<Trader> trader_list = new ArrayList<Trader>();

    public TraderSpinnerAdapter(@NonNull Context context, int textViewResourceId, ArrayList<Trader> trader_list) {
        super(context, textViewResourceId, trader_list);
        mContext = context;
        this.trader_list = trader_list;
    }

    public void setPos(int actPosition){
        this.actPosition = actPosition;
    }

    public Trader getItem() {
        return trader_list.get(actPosition);
    }

    @Override
    public int getCount() {
        return trader_list.size();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setText(trader_list.get(position).getName());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getView(position,  convertView, parent);
    }

    public int getPosition(int TraderId) {
        int count = 0;
        for (Trader trader : trader_list) {
            if (trader.getTraderId() == TraderId) {
                return count;
            }
            count++;
        }
        return -1;
    }
}