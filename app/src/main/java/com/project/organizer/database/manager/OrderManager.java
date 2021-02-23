package com.project.organizer.database.manager;

import com.project.organizer.database.dbo.OrderDbo;
import com.project.organizer.database.dbo.TraderDetailsDbo;
import com.project.organizer.database.helper.UpdateParameter;
import com.project.organizer.database.helper.WhereParameter;

import java.util.List;

public class OrderManager extends ManagerBase<OrderDbo> {
    @Override
    protected OrderDbo createNew() {
        return new OrderDbo();
    }

    public OrderDbo getById(int id) throws Exception {
        return getFirst(getBy(new WhereParameter(OrderDbo.class.getField("orderId"), id)));
    }

    public List<OrderDbo> getActiveOrder() throws Exception {
        return getBy(new WhereParameter(OrderDbo.class.getField("isClosed"), 0));
    }

    public void setIsClosed(int orderId, int isClosed) throws Exception {
        OrderDbo orderDbo = new OrderDbo();
        orderDbo.orderId = orderId;

        this.update(orderDbo, new UpdateParameter(OrderDbo.class.getField("isClosed"), isClosed));
    }
}

