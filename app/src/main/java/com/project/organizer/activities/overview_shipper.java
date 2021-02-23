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
import com.project.organizer.helper.adapter.ShipperAdapter;
import com.project.organizer.model.shipper.Shipper;

import java.util.ArrayList;
import java.util.List;

public class overview_shipper extends AppCompatActivity {
    ShipperAdapter sAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview_shipper);
        setTitle(R.string.headline_shipper_overview);
        Button btn_new_shipper = findViewById(R.id.btn_new_shipper);

        RecyclerView recyclerView = findViewById(R.id.rv_ov_shipper);
        List<Shipper> shipper_list = null;
        try {
            shipper_list = OverviewService.getShipper();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (shipper_list != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            sAdapter = new ShipperAdapter(this, (ArrayList<Shipper>) shipper_list);
            sAdapter.setClickListener(this::onItemClick);
            recyclerView.setAdapter(sAdapter);
        } else {
            Toast.makeText(this, R.string.no_shipper, Toast.LENGTH_LONG).show();
        }

        // Bestellung anlegen
        btn_new_shipper.setOnClickListener(v -> {
            Intent i = new Intent(overview_shipper.this, shipper_details.class);
            try {
                i.putExtra("ACTION", "CREATE");

            } catch (Exception e) {
                e.printStackTrace();
            }
            startActivity(i);
        });

    }

    public void onItemClick(View view, int position) {
        sAdapter.getItem(position);

       Intent i = new Intent(overview_shipper.this, shipper_details.class);
        try {
            i.putExtra("ACTION", "CHANGE");
            i.putExtra("IDENTITY", String.valueOf(sAdapter.getItem(position).getShipperId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        startActivity(i);
    }
}