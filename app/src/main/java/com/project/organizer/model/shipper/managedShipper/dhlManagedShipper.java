package com.project.organizer.model.shipper.managedShipper;

import com.project.organizer.model.Order;
import com.project.organizer.model.shipper.Shipper;
import com.project.organizer.model.shipper.ShippingStatus;

import java.util.List;

public class dhlManagedShipper extends Shipper {

    public static String Identifier = "52d061d0-df6a-4fb2-a149-3f58233c9cbe";

    public dhlManagedShipper() {
        super();
    }

    public dhlManagedShipper(int shipperId, String name, String url, boolean active, boolean api) {
       super(shipperId, name,  url,  active,  api);
    }

    @Override
    public String getShippingStatus(Order order)
    {
        // API Aufruf

        return null;
    }

    @Override
    public String getIdentifier() {
        return Identifier;
    }
}
