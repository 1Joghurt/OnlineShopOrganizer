package com.project.organizer.helper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.project.organizer.R;
import com.project.organizer.model.trader.Trader;
import java.util.ArrayList;


public class TraderAdapter extends RecyclerView.Adapter<TraderAdapter.ViewHolder> {

    private ArrayList<Trader> mData = new ArrayList<Trader>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    public TraderAdapter(Context context, ArrayList<Trader> data) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (!mData.get(position).isApi()) {
            holder.myTextView.setText( mData.get(position).getName() +" (" + context.getString(R.string.manual_tag) + ")");
        } else {
            holder.myTextView.setText(mData.get(position).getName());
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.tvRVName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view , getAdapterPosition());
        }
    }

    public Trader getItem(int id) {
        return mData.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}


