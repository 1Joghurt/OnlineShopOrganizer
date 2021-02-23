package com.project.organizer.database.mapper;

import com.project.organizer.database.dbo.ShippingDetailsDbo;
import com.project.organizer.model.Order;

public class ShippingDetailsMapper {
    public static ShippingDetailsDbo convertToDbo(Order order) {
        ShippingDetailsDbo dbo = new ShippingDetailsDbo();

        dbo.orderId = order.getId();
        dbo.shipperId = order.getShipper().getShipperId();
        dbo.trackingnumber = order.getTrackingnumber();
        dbo.shipperStatusId = order.getShippingStatus() == null ? 0 : order.getShippingStatus().getId();

        return dbo;
    }
}
