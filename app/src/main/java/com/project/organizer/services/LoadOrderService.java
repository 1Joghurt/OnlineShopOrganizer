package com.project.organizer.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.project.organizer.helper.OverviewService;
import com.project.organizer.model.Order;

import java.util.ArrayList;
import java.util.List;

public class LoadOrderService extends Service {
    private static List<Order> orders = new ArrayList<Order>();

    public static List<Order> getOrders() {
        return orders;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.getOrders(intent);
        return Service.START_REDELIVER_INTENT;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void getOrders(Intent intent) {
        Runnable runnableLoadOrders = new Runnable() {
            @Override
            public void run() {
                String traderIdentifier = intent.hasExtra("IDENTIFIER") ? intent.getStringExtra("IDENTIFIER") : null;

                try {
                    if(traderIdentifier != null && traderIdentifier != "") {
                        orders.addAll(OverviewService.getNewOrders(traderIdentifier));
                    }
                    else {
                        orders.clear();
                        boolean reload = intent.hasExtra("WIFI") && intent.getBooleanExtra("WIFI", false);
                        orders.addAll(OverviewService.getActiveOrders(reload));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                sendBroadcast();
            }
        };

        Thread threadLoadOrders = new Thread(runnableLoadOrders);
        threadLoadOrders.start();
    }

    private void sendBroadcast() {
        Intent intent = new Intent("NewOrders");
        sendBroadcast(intent);
    }
}