package com.project.organizer.model.shipper;

import com.project.organizer.model.Order;

import java.util.List;

public final class ManualShipper extends Shipper {
    private List<ShippingStatus> shipperStatusList;

    public ManualShipper() {
        super();
    }

    public ManualShipper(int shipperId, String name, List<ShippingStatus> shipperStatusList) {
        super(shipperId, name, "", true,false);
        this.shipperStatusList = shipperStatusList;
    }

    public List<ShippingStatus> getShipperStatusList() {
        return shipperStatusList;
    }

    public void setShipperStatusList(List<ShippingStatus> shipperStatusList) {
        this.shipperStatusList = shipperStatusList;
    }

    @Override
    public String getShippingStatus(Order order)
    {
        return order.getShippingStatus().getName();
    }

    @Override
    public String getIdentifier() {
        return null;
    }
}
