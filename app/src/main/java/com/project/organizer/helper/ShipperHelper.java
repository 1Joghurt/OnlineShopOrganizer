package com.project.organizer.helper;

import com.project.organizer.database.DatabaseService;
import com.project.organizer.model.Order;
import com.project.organizer.model.shipper.ManualShipper;
import com.project.organizer.model.shipper.Shipper;
import com.project.organizer.model.shipper.ShippingStatus;
import com.project.organizer.model.shipper.managedShipper.dhlManagedShipper;

import java.util.List;

public class ShipperHelper {
    public static String getShippingStatus(Order order) {
        return order.getShipper().getShippingStatus(order);
    }

    public static void saveShipper(Shipper shipper) throws Exception {
        DatabaseService.getInstance().saveShipper(shipper);
    }

    public static void updateShippingStatus(Order order, ShippingStatus shippingStatus) throws Exception {
        if(order.getShipper().isApi()) {
            return;
        }

        DatabaseService.getInstance().updateShippingStatus(order, shippingStatus);
    }

    public static List<Shipper> getShipper() throws Exception {
        return DatabaseService.getInstance().getShipper();
    }

    public static Shipper getNewShipper(String identifier) {
        if(dhlManagedShipper.Identifier == identifier) {
            return new dhlManagedShipper();
        }

        return new ManualShipper();
    }
}
