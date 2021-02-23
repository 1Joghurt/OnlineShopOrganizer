package com.project.organizer.database.mapper;

import com.project.organizer.database.dbo.OrderStatusDbo;
import com.project.organizer.database.helper.IntBooleanConverter;
import com.project.organizer.model.trader.OrderStatus;

public class OrderStatusMapper {
    public static OrderStatusDbo convertToDbo(OrderStatus orderStatus) {
        OrderStatusDbo dbo = new OrderStatusDbo();

        dbo.orderStatusId = orderStatus.getId();
        dbo.name = orderStatus.getName();
        dbo.isClosed = IntBooleanConverter.getInt(orderStatus.getIsClosed());

        return dbo;
    }

    public static OrderStatus convertToModel(OrderStatusDbo dbo) {
        return new OrderStatus(dbo.orderStatusId, dbo.name, IntBooleanConverter.getBoolean(dbo.isClosed));
    }
}
