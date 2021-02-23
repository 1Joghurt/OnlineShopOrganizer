package com.project.organizer.database.mapper;

import com.project.organizer.database.dbo.TraderDbo;
import com.project.organizer.database.helper.IntBooleanConverter;
import com.project.organizer.helper.TraderHelper;
import com.project.organizer.model.trader.ManualTrader;
import com.project.organizer.model.trader.OrderStatus;
import com.project.organizer.model.trader.Trader;

import java.util.List;

public class TraderMapper {
    public static TraderDbo convertToDbo(Trader trader) {
        TraderDbo dbo = new TraderDbo();

        dbo.traderId = trader.getTraderId();
        dbo.name = trader.getName();
        dbo.url = trader.getUrl();
        dbo.active = IntBooleanConverter.getInt(trader.isActive());
        dbo.api = IntBooleanConverter.getInt(trader.isApi());
        dbo.identifier = trader.getIdentifier();
        dbo.user = trader.getUser();
        dbo.password = trader.getPassword();
        dbo.mail = trader.getMail();

        return dbo;
    }

    public static Trader convertToModel(TraderDbo dbo, List<OrderStatus> orderStatusList) {
        Trader trader = TraderHelper.getNewTrader(dbo.identifier);

        trader.setTraderId(dbo.traderId);
        trader.setName(dbo.name);
        trader.setUrl(dbo.url);
        trader.setActive(IntBooleanConverter.getBoolean(dbo.active));
        trader.setApi(IntBooleanConverter.getBoolean(dbo.api));
        trader.setUser(dbo.user);
        trader.setPassword(dbo.password);
        trader.setMail(dbo.mail);

        if(trader instanceof ManualTrader) {
            ManualTrader manualTrader = (ManualTrader)trader;
            manualTrader.setOrderStatusList(orderStatusList);
        }

        return trader;
    }
}
