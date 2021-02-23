package com.project.organizer.database.manager;

import com.project.organizer.database.dbo.OrderStatusDbo;
import com.project.organizer.database.helper.WhereParameter;

public class OrderStatusManager extends ManagerBase<OrderStatusDbo> {
    @Override
    protected OrderStatusDbo createNew() {
        return new OrderStatusDbo();
    }

    public OrderStatusDbo getById(int orderStatusId) throws Exception {
        return getFirst(getBy(new WhereParameter(OrderStatusDbo.class.getField("orderStatusId"), orderStatusId)));
    }
}
