package com.project.organizer.database.manager;

import com.project.organizer.database.dbo.TraderDbo;
import com.project.organizer.database.helper.WhereParameter;

import java.util.List;

public class TraderManager extends ManagerBase<TraderDbo> {
    @Override
    protected TraderDbo createNew() {
        return new TraderDbo();
    }

    public TraderDbo getById(int id) throws Exception {
        return getFirst(getBy(new WhereParameter(TraderDbo.class.getField("traderId"), id)));
    }

    public List<TraderDbo> getActiveTraders() throws Exception {
        return getBy(new WhereParameter(TraderDbo.class.getField("active"), 1));
    }
}
