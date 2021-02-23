package com.project.organizer.model.trader.managedTrader;

import com.project.organizer.model.Order;
import com.project.organizer.model.trader.IManagedTrader;
import com.project.organizer.model.trader.OrderStatus;
import com.project.organizer.model.trader.Trader;

import java.util.ArrayList;
import java.util.List;

public class ebayManagedTrader extends Trader implements IManagedTrader {

    public static String Identifier = "9121f760-3f6c-47fb-b78c-01f7e1920490";

    public ebayManagedTrader() {
        super();
    }

    public ebayManagedTrader(int traderId, String name, String url, boolean active, boolean api, String user, String password, String mail) {
        super(traderId, name, url, active, api, user, password, mail);
    }

    @Override
    public List<Order> getAllOrders() {
        return new ArrayList<>();
    }

    @Override
    public String getOrderStatus(Order order) {
        return "";
    }

    @Override
    public String getIdentifier() {
        return Identifier;
    }
}
