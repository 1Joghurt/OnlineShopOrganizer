package com.project.organizer.model.shipper;

import com.project.organizer.model.Order;

public class ShippingStatus {
    private int id;
    private String name;

    public ShippingStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}