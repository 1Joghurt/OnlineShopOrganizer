package com.project.organizer.model.shipper;

import com.project.organizer.model.Order;

import java.util.List;

public abstract class Shipper {
    private int shipperId;
    private String name;
    private String url;
    private boolean active;
    private boolean api;

    public Shipper() {
    }

    public Shipper(int shipperId, String name, String url, boolean active, boolean api) {
        this.shipperId = shipperId;
        this.name = name;
        this.url = url;
        this.active = active;
        this.api = api;
    }

    public int getShipperId() {
        return shipperId;
    }

    public void setShipperId(int shipperId) {
        this.shipperId = shipperId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isApi() {
        return api;
    }

    public void setApi(boolean api) {
        this.api = api;
    }

    public abstract String getIdentifier();

    public abstract String getShippingStatus(Order order);
}
