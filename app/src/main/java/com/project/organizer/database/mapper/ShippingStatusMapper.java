package com.project.organizer.database.mapper;

import com.project.organizer.database.dbo.ShippingStatusDbo;
import com.project.organizer.model.shipper.ShippingStatus;

public class ShippingStatusMapper {
    public static ShippingStatusDbo convertToDbo(ShippingStatus shippingStatus) {
        ShippingStatusDbo dbo = new ShippingStatusDbo();

        dbo.shippingStatusId = shippingStatus.getId();
        dbo.name = shippingStatus.getName();

        return dbo;
    }

    public static ShippingStatus convertToModel(ShippingStatusDbo dbo) {
        return new ShippingStatus(dbo.shippingStatusId, dbo.name);
    }
}
