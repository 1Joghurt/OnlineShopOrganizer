package com.project.organizer.database.manager;

import com.project.organizer.database.dbo.OrderStatusMappingDbo;
import com.project.organizer.database.helper.WhereParameter;

import java.util.List;

public class OrderStatusMappingManager extends ManagerBase<OrderStatusMappingDbo> {
    @Override
    protected OrderStatusMappingDbo createNew() {
        return new OrderStatusMappingDbo();
    }

    public List<OrderStatusMappingDbo> getByTraderId(int traderId) throws Exception {
        return getBy(new WhereParameter(OrderStatusMappingDbo.class.getField("traderId"), traderId));
    }

    public void deleteByTraderId(int traderId) throws Exception {
        this.delete(new WhereParameter(OrderStatusMappingDbo.class.getField("traderId"), traderId));
    }
}
