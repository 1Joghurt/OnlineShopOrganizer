package com.project.organizer.database.mapper;

import com.project.organizer.database.dbo.OrderDbo;
import com.project.organizer.database.helper.DatetimeConverter;
import com.project.organizer.database.helper.IntBooleanConverter;
import com.project.organizer.model.Order;
import com.project.organizer.model.shipper.Shipper;
import com.project.organizer.model.shipper.ShippingStatus;
import com.project.organizer.model.trader.OrderStatus;
import com.project.organizer.model.trader.Trader;

public class OrderMapper {
    public static OrderDbo convertToDbo(Order order) {
        OrderDbo dbo = new OrderDbo();

        dbo.orderId = order.getId();
        dbo.date = DatetimeConverter.getString(order.getDate());
        dbo.note = order.getNote();
        dbo.isClosed = IntBooleanConverter.getInt(order.isClosed());

        return dbo;
    }

    public static Order convertToModel(OrderDbo dbo, Shipper shipper, String trackingnumber, ShippingStatus shippingStatus, Trader trader, String ordernumber, OrderStatus orderStatus) {
        return new Order(dbo.orderId, IntBooleanConverter.getBoolean(dbo.isClosed), dbo.note, DatetimeConverter.getDateTime(dbo.date), shipper, trackingnumber, shippingStatus, trader, ordernumber, orderStatus);
    }
}

