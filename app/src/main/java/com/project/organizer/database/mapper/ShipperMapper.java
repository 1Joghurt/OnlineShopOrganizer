package com.project.organizer.database.mapper;

import com.project.organizer.database.dbo.ShipperDbo;
import com.project.organizer.database.helper.IntBooleanConverter;
import com.project.organizer.helper.ShipperHelper;
import com.project.organizer.model.shipper.ManualShipper;
import com.project.organizer.model.shipper.Shipper;
import com.project.organizer.model.shipper.ShippingStatus;
import com.project.organizer.model.trader.ManualTrader;

import java.util.List;

public class ShipperMapper {
    public static ShipperDbo convertToDbo(Shipper shipper) {
        ShipperDbo dbo = new ShipperDbo();

        dbo.shipperId = shipper.getShipperId();
        dbo.name = shipper.getName();
        dbo.url = shipper.getUrl();
        dbo.active = IntBooleanConverter.getInt(shipper.isActive());
        dbo.api = IntBooleanConverter.getInt(shipper.isApi());
        dbo.identifier = shipper.getIdentifier();

        return dbo;
    }

    public static Shipper convertToModel(ShipperDbo dbo, List<ShippingStatus> shipperStatusList) {
        Shipper shipper = ShipperHelper.getNewShipper(dbo.identifier);

        shipper.setShipperId(dbo.shipperId);
        shipper.setName(dbo.name);
        shipper.setUrl(dbo.url);
        shipper.setActive(IntBooleanConverter.getBoolean(dbo.active));
        shipper.setApi(IntBooleanConverter.getBoolean(dbo.api));

        if(shipper instanceof ManualShipper) {
            ManualShipper manualShipper = (ManualShipper)shipper;
            manualShipper.setShipperStatusList(shipperStatusList);
        }

        return shipper;
    }
}
