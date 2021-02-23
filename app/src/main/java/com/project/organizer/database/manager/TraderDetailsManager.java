package com.project.organizer.database.manager;

import com.project.organizer.database.dbo.TraderDetailsDbo;
import com.project.organizer.database.helper.UpdateParameter;
import com.project.organizer.database.helper.WhereParameter;

public class TraderDetailsManager extends ManagerBase<TraderDetailsDbo> {
    @Override
    protected TraderDetailsDbo createNew() {
        return new TraderDetailsDbo();
    }

    public TraderDetailsDbo getByOrderId(int id) throws Exception {
        return getFirst(getBy(new WhereParameter(TraderDetailsDbo.class.getField("orderId"), id)));
    }

    public void setStatusId(int orderId, int statusId) throws Exception {
        TraderDetailsDbo traderDetailsDbo = new TraderDetailsDbo();
        traderDetailsDbo.orderId = orderId;

        this.update(traderDetailsDbo, new UpdateParameter(TraderDetailsDbo.class.getField("orderStatusId"), statusId));
    }
}
