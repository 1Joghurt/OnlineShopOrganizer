package com.project.organizer.database.manager;

import com.project.organizer.database.dbo.ShippingDetailsDbo;
import com.project.organizer.database.helper.UpdateParameter;
import com.project.organizer.database.helper.WhereParameter;

public class ShippingDetailsManager extends ManagerBase<ShippingDetailsDbo> {
    @Override
    protected ShippingDetailsDbo createNew() {
        return new ShippingDetailsDbo();
    }

    public ShippingDetailsDbo getByOrderId(int id) throws Exception {
        return getFirst(getBy(new WhereParameter(ShippingDetailsDbo.class.getField("orderId"), id)));
    }

    public void setStatusId(int orderId, int statusId) throws Exception {
        ShippingDetailsDbo shippingDetailsDbo = new ShippingDetailsDbo();
        shippingDetailsDbo.orderId = orderId;

        this.update(shippingDetailsDbo, new UpdateParameter(ShippingDetailsDbo.class.getField("shipperStatusId"), statusId));
    }
}
