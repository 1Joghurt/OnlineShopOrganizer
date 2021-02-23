package com.project.organizer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.organizer.R;
import com.project.organizer.helper.OverviewService;
import com.project.organizer.helper.adapter.TraderAdapter;
import com.project.organizer.model.trader.Trader;

import java.util.ArrayList;
import java.util.List;

public class overview_trader extends AppCompatActivity {
    TraderAdapter tAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview_trader);
        setTitle(R.string.headline_trader_overview);
        Button btn_new_trader = findViewById(R.id.btn_new_trader);

        RecyclerView recyclerView = findViewById(R.id.rv_ov_trader);
        List<Trader> traderList = null;
        try {
            traderList = OverviewService.getTrader();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (traderList != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            tAdapter = new TraderAdapter(this, (ArrayList<Trader>) traderList);
            tAdapter.setClickListener(this::onItemClick);
            recyclerView.setAdapter(tAdapter);
        } else {
            Toast.makeText(this, R.string.no_trader, Toast.LENGTH_LONG).show();
        }

        // Bestellung anlegen
        btn_new_trader.setOnClickListener(v -> {
            Intent i = new Intent(overview_trader.this, trader_details.class);
            try {
                i.putExtra("ACTION", "CREATE");

            } catch (Exception e) {
                e.printStackTrace();
            }
            startActivity(i);
        });
    }

    public void onItemClick(View view, int position) {
        tAdapter.getItem(position);

       Intent i = new Intent(overview_trader.this, trader_details.class);
        try {
            i.putExtra("ACTION", "CHANGE");
            i.putExtra("IDENTITY", String.valueOf(tAdapter.getItem(position).getTraderId()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        startActivity(i);

    }
}