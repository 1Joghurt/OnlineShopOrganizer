package com.project.organizer.database.manager;

import com.project.organizer.database.dbo.ShippingStatusMappingDbo;
import com.project.organizer.database.helper.WhereParameter;

import java.util.List;

public class ShippingStatusMappingManager extends ManagerBase<ShippingStatusMappingDbo> {
    @Override
    protected ShippingStatusMappingDbo createNew() {
        return new ShippingStatusMappingDbo();
    }

    public List<ShippingStatusMappingDbo> getByShipperId(int shipperId) throws Exception {
        return getBy(new WhereParameter(ShippingStatusMappingDbo.class.getField("shipperId"), shipperId));
    }

    public void deleteByShipperId(int shipperId) throws Exception {
        this.delete(new WhereParameter(ShippingStatusMappingDbo.class.getField("shipperId"), shipperId));
    }
}
