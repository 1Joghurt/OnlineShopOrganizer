package com.project.organizer.model.trader;

import com.project.organizer.model.Order;

public abstract class Trader {

    private int traderId;
    private String name = "";
    private String url;
    private boolean active;
    private boolean api;
    private String user;
    private String password;
    private String mail;

    public Trader() {

    }

    public Trader(int traderId, String name, String url, boolean active, boolean api, String user, String password, String mail) {
        this.traderId = traderId;
        this.name = name;
        this.url = url;
        this.active = active;
        this.api = api;
        this.user = user;
        this.password = password;
        this.mail = mail;
    }

    public int getTraderId() {
        return traderId;
    }

    public void setTraderId(int traderId) {
        this.traderId = traderId;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public abstract String getOrderStatus(Order order);

    public abstract String getIdentifier();
}
