package com.project.organizer.helper;

import com.project.organizer.database.DatabaseService;
import com.project.organizer.model.Order;
import com.project.organizer.model.trader.IManagedTrader;
import com.project.organizer.model.trader.ManualTrader;
import com.project.organizer.model.trader.OrderStatus;
import com.project.organizer.model.trader.Trader;
import com.project.organizer.model.trader.managedTrader.amazonManagedTrader;
import com.project.organizer.model.trader.managedTrader.ebayManagedTrader;

import java.util.ArrayList;
import java.util.List;

public class TraderHelper {

    public static List<Order> getNewOrders() throws Exception {
        List<Order> orders = new ArrayList<Order>();

        for(Trader trader : getTrader()) {
            if(trader instanceof IManagedTrader) {
                IManagedTrader managedTrader = (IManagedTrader)trader;
                List<Order> newOrders = getNewOrders(managedTrader);
                if(newOrders != null) {
                    orders.addAll(newOrders);
                }
            }
        }

        return orders;
    }

    public static List<Order> getNewOrders(IManagedTrader trader) {
        List<Order> orders = trader.getAllOrders();

        for(Order order : orders) {
            try {
                DatabaseService.getInstance().saveOrder(order);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return orders;
    }

    public static void saveTrader(Trader trader) throws Exception {
        DatabaseService.getInstance().saveTrader(trader);
    }

    public static void updateOrderStatus(Order order, OrderStatus orderStatus) throws Exception {
        if(order.getTrader().isApi()) {
            return;
        }

        DatabaseService.getInstance().updateOrderStatus(order, orderStatus);
    }

    public static String getOrderStatus(Order order) {
        return order.getTrader().getOrderStatus(order);
    }

    public static IManagedTrader getManagedTrader(String identifier) throws Exception {
        for(Trader trader : getTrader()) {
            if(trader.getIdentifier().equals(identifier) && trader instanceof IManagedTrader) {
                IManagedTrader managedTrader = (IManagedTrader)trader;
                return managedTrader;
            }
        }

        return null;
    }

    public static List<Trader> getTrader() throws Exception {
        return DatabaseService.getInstance().getTrader();
    }

    public static Trader getNewTrader(String identifier) {
        if(identifier != null && identifier != "") {
            if(ebayManagedTrader.Identifier.equals(identifier)) {
                return new ebayManagedTrader();
            }
            else if(amazonManagedTrader.Identifier.equals(identifier)) {
                return new amazonManagedTrader();
            }
        }

        return new ManualTrader();
    }
}
