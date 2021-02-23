package com.project.organizer.database.manager;

import com.project.organizer.database.dbo.ShipperDbo;
import com.project.organizer.database.helper.WhereParameter;

import java.util.List;

public class ShipperManager extends ManagerBase<ShipperDbo> {
    @Override
    protected ShipperDbo createNew() {
        return new ShipperDbo();
    }

    public ShipperDbo getById(int id) throws Exception {
        return getFirst(getBy(new WhereParameter(ShipperDbo.class.getField("shipperId"), id)));
    }

    public List<ShipperDbo> getActiveShippers() throws Exception {
        return getBy(new WhereParameter(ShipperDbo.class.getField("active"), 1));
    }
}
