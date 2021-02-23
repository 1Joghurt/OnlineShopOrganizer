package com.project.organizer.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.organizer.R;
import com.project.organizer.database.DatabaseTest;
import com.project.organizer.helper.adapter.OrderAdapter;
import com.project.organizer.helper.OverviewService;
import com.project.organizer.helper.ShutdownReceiver;
import com.project.organizer.model.Order;
import com.project.organizer.services.LoadOrderService;

import java.util.ArrayList;
import java.util.List;


public class overview extends AppCompatActivity {
    OrderAdapter oAdapter;
    static BroadcastReceiver br;
    private static boolean isStartup = true;
    boolean wifiAvailable = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        setTitle(R.string.headline_order_overview);

        startupApp();

        RecyclerView recyclerView = findViewById(R.id.rv_overview);
        Button btn_new_order = findViewById(R.id.btn_new_order);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        oAdapter = new OrderAdapter(this, new ArrayList<>());
        oAdapter.setClickListener(this::onItemClick);
        recyclerView.setAdapter(oAdapter);

        BroadcastReceiver broadcastReceiverOrders = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                List<Order> order = LoadOrderService.getOrders();
                oAdapter.setData(new ArrayList<>(order));
            }
        };
        IntentFilter intentFilterOrders = new IntentFilter("NewOrders");
        registerReceiver(broadcastReceiverOrders, intentFilterOrders);

        Intent loadOrdersIntent = new Intent(overview.this, LoadOrderService.class);
        loadOrdersIntent.putExtra("WIFI", wifiAvailable);
        startService(loadOrdersIntent);

        // Bestellung anlegen
        btn_new_order.setOnClickListener(v -> {
            Intent i = new Intent(overview.this, order_details.class);
            try {
                i.putExtra("ACTION", "CREATE");

            } catch (Exception e) {
                e.printStackTrace();
            }
            startActivity(i);
        });
    }

    public void onItemClick(View view, int position) {
        Intent i = new Intent(overview.this, order_details.class);
        try {
            i.putExtra("ACTION", "CHANGE");
            i.putExtra("IDENTITY", String.valueOf(oAdapter.getItem(position).getId()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_overview, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.show_shipper) {
            Intent i = new Intent(overview.this, overview_shipper.class);
            startActivity(i);
            return true;
        } else if (id == R.id.show_trader) {
            Intent i = new Intent(overview.this, overview_trader.class);
            startActivity(i);
            return true;
        } else if(id == R.id.reload_order) {
            Intent i = new Intent(overview.this, LoadOrderService.class);
            i.putExtra("WIFI", wifiAvailable);
            startService(i);
            return true;
        } else if(id == R.id.add_dummys) {
            DatabaseTest.createTestdaten();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void startupApp()  {
        if (isStartup == true)
        {
            wifiAvailable = false;
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo;
            for (Network mNetwork : connMgr.getAllNetworks()) {
                networkInfo = connMgr.getNetworkInfo(mNetwork);
                if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI && networkInfo.isConnected()) {
                    wifiAvailable = true;
                    break;
                }
            }
            if (!wifiAvailable) {
                Toast.makeText(this,R.string.no_wifi ,Toast.LENGTH_SHORT).show();}
            isStartup =false;
        }
    }
}