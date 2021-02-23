package com.project.organizer.database.manager;

import com.project.organizer.database.dbo.ShippingStatusDbo;
import com.project.organizer.database.helper.WhereParameter;

public class ShippingStatusManager extends ManagerBase<ShippingStatusDbo> {
    @Override
    protected ShippingStatusDbo createNew() {
        return new ShippingStatusDbo();
    }

    public ShippingStatusDbo getById(int shippingStatusId) throws Exception {
        return getFirst(getBy(new WhereParameter(ShippingStatusDbo.class.getField("shippingStatusId"), shippingStatusId)));
    }
}
