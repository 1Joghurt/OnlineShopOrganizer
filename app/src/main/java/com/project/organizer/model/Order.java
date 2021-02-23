package com.project.organizer.model;

import com.project.organizer.model.shipper.Shipper;
import com.project.organizer.model.shipper.ShippingStatus;
import com.project.organizer.model.trader.OrderStatus;
import com.project.organizer.model.trader.Trader;


import java.io.Serializable;
import java.time.LocalDateTime;

public class Order {

    private int id;

    private boolean isClosed;
    private String note;
    private LocalDateTime date;

    private Shipper shipper;
    private String trackingnumber;
    private ShippingStatus shippingStatus;

    private Trader trader;
    private String ordernumber;
    private OrderStatus orderStatus;

    public Order(int id, boolean isClosed, String note, LocalDateTime date, Shipper shipper, String trackingnumber, ShippingStatus shippingStatus, Trader trader, String ordernumber, OrderStatus orderStatus) {
        this.id = id;
        this.isClosed = isClosed;
        this.note = note;
        this.date = date;
        this.shipper = shipper;
        this.trackingnumber = trackingnumber;
        this.shippingStatus = shippingStatus;
        this.trader = trader;
        this.ordernumber = ordernumber;
        this.orderStatus = orderStatus;
    }
    public Order()
    {

    }

    public Order(int id, Trader trader, Shipper shipper, String note, LocalDateTime date)
    {
        this.id = id;
        this.setDate(date);
        this.setTrader(trader);
        this.setShipper(shipper);
        this.setClosed(false);
        this.setNote(note);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Shipper getShipper() {
        return shipper;
    }

    public void setShipper(Shipper shipper) {
        this.shipper = shipper;
    }

    public String getTrackingnumber() {
        return trackingnumber;
    }

    public void setTrackingnumber(String trackingnumber) {
        this.trackingnumber = trackingnumber;
    }

    public ShippingStatus getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(ShippingStatus shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public Trader getTrader() {
        return trader;
    }

    public void setTrader(Trader trader) {
        this.trader = trader;
    }

    public String getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
