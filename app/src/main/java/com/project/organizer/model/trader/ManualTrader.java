package com.project.organizer.model.trader;

import com.project.organizer.model.Order;

import java.util.List;

public final class ManualTrader extends Trader {

    private List<OrderStatus> orderStatusList;

    public ManualTrader(){

    }

    public ManualTrader(int traderId, String name, String mail, List<OrderStatus> orderStatusList) {
        super(traderId, name, "", true, false, "", "", mail);
        this.orderStatusList = orderStatusList;
    }

    public List<OrderStatus> getOrderStatusList() {
        return orderStatusList;
    }

    public void setOrderStatusList(List<OrderStatus> orderStatusList) {
        this.orderStatusList = orderStatusList;
    }


    @Override
    public String getOrderStatus(Order order) {
        return null;
    }

    @Override
    public String getIdentifier() {
        return null;
    }
}
