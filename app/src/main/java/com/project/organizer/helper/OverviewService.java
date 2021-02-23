package com.project.organizer.helper;

import com.project.organizer.database.DatabaseService;
import com.project.organizer.model.Order;
import com.project.organizer.model.shipper.Shipper;
import com.project.organizer.model.shipper.ShippingStatus;
import com.project.organizer.model.trader.IManagedTrader;
import com.project.organizer.model.trader.OrderStatus;
import com.project.organizer.model.trader.Trader;

import java.util.ArrayList;

import java.util.List;

public class OverviewService {

    public static List<Order> getActiveOrders(boolean reload) throws Exception {
        ArrayList<Order> activeOrders = new ArrayList<Order>();
        activeOrders.addAll(getLocalOrders());
        if (reload) {
            activeOrders.addAll(getNewOrders());
        }
        return activeOrders;
    }
    public static List<Order> getLocalOrders() throws Exception {
        return DatabaseService.getInstance().getActiveOrder();
    }

    public static Order getOrder(int id) throws Exception {
        return DatabaseService.getInstance().getOrder(id);
    }

    public static String getOrderStatus(Order order) {
        return TraderHelper.getOrderStatus(order);
    }

    public static String getShippingStatus(Order order) {
        return ShipperHelper.getShippingStatus(order);
    }

    public static void updateOrderStatus(Order order, OrderStatus orderStatus) throws Exception {
        TraderHelper.updateOrderStatus(order, orderStatus);

        if(orderStatus.getIsClosed() && !order.isClosed()) {
            DatabaseService.getInstance().updateOrderIsClosed(order, true);
        }
    }

    public static void updateShippingStatus(Order order, ShippingStatus shippingStatus) throws Exception {
        ShipperHelper.updateShippingStatus(order, shippingStatus);
    }

    public static List<Order> getNewOrders() throws Exception {
        return TraderHelper.getNewOrders();
    }

    public static List<Order> getNewOrders(String traderIdentifier) throws Exception {
        IManagedTrader trader = TraderHelper.getManagedTrader(traderIdentifier);

        if(trader == null) {
            return new ArrayList<>();
        }

        return TraderHelper.getNewOrders(trader);
    }

    public static List<Trader> getTrader() throws Exception {
        return TraderHelper.getTrader();
    }

    public static List<Shipper> getShipper() throws Exception {
        return ShipperHelper.getShipper();
    }

    public static Trader getTrader(int Id) throws Exception {
        return DatabaseService.getInstance().getTrader(Id);
    }

    public static Shipper getShipper(int Id) throws Exception {
        return DatabaseService.getInstance().getShipper(Id);
    }

    public static void saveTrader(Trader trader) throws Exception {
        TraderHelper.saveTrader(trader);
    }

    public static void saveShipper(Shipper shipper) throws Exception {
        ShipperHelper.saveShipper(shipper);
    }

    public static void saveOrder(Order order) throws Exception {
        if(order.getOrderStatus() != null  && order.getOrderStatus().getIsClosed() && !order.isClosed()) {
            order.setClosed(true);
        }
        DatabaseService.getInstance().saveOrder(order);
    }

}
