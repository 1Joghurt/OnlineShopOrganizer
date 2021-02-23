package com.project.organizer.database.mapper;

import com.project.organizer.database.dbo.TraderDetailsDbo;
import com.project.organizer.model.Order;

public class TraderDetailsMapper {
    public static TraderDetailsDbo convertToDbo(Order order) {
        TraderDetailsDbo dbo = new TraderDetailsDbo();

        dbo.orderId = order.getId();
        dbo.traderId = order.getTrader().getTraderId();
        dbo.ordernumber = order.getOrdernumber();
        dbo.orderStatusId = order.getOrderStatus() == null ? 0 : order.getOrderStatus().getId();

        return dbo;
    }
}
