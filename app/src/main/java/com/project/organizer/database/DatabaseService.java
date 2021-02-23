package com.project.organizer.database;

import com.project.organizer.database.dbo.OrderDbo;
import com.project.organizer.database.dbo.OrderStatusDbo;
import com.project.organizer.database.dbo.OrderStatusMappingDbo;
import com.project.organizer.database.dbo.ShipperDbo;
import com.project.organizer.database.dbo.ShippingDetailsDbo;
import com.project.organizer.database.dbo.ShippingStatusDbo;
import com.project.organizer.database.dbo.ShippingStatusMappingDbo;
import com.project.organizer.database.dbo.TraderDbo;
import com.project.organizer.database.dbo.TraderDetailsDbo;
import com.project.organizer.database.helper.IntBooleanConverter;
import com.project.organizer.database.manager.OrderManager;
import com.project.organizer.database.manager.OrderStatusManager;
import com.project.organizer.database.manager.OrderStatusMappingManager;
import com.project.organizer.database.manager.ShipperManager;
import com.project.organizer.database.manager.ShippingDetailsManager;
import com.project.organizer.database.manager.ShippingStatusManager;
import com.project.organizer.database.manager.ShippingStatusMappingManager;
import com.project.organizer.database.manager.TraderDetailsManager;
import com.project.organizer.database.manager.TraderManager;
import com.project.organizer.database.mapper.OrderMapper;
import com.project.organizer.database.mapper.OrderStatusMapper;
import com.project.organizer.database.mapper.ShipperMapper;
import com.project.organizer.database.mapper.ShippingDetailsMapper;
import com.project.organizer.database.mapper.ShippingStatusMapper;
import com.project.organizer.database.mapper.TraderDetailsMapper;
import com.project.organizer.database.mapper.TraderMapper;
import com.project.organizer.model.Order;
import com.project.organizer.model.shipper.ManualShipper;
import com.project.organizer.model.shipper.Shipper;
import com.project.organizer.model.shipper.ShippingStatus;
import com.project.organizer.model.trader.ManualTrader;
import com.project.organizer.model.trader.OrderStatus;
import com.project.organizer.model.trader.Trader;

import java.util.ArrayList;
import java.util.List;

public class DatabaseService {

    private final OrderManager orderManager = new OrderManager();
    private final OrderStatusManager orderStatusManager = new OrderStatusManager();
    private final OrderStatusMappingManager orderStatusMappingManager = new OrderStatusMappingManager();
    private final ShipperManager shipperManager = new ShipperManager();
    private final ShippingDetailsManager shippingDetailsManager = new ShippingDetailsManager();
    private final ShippingStatusManager shippingStatusManager = new ShippingStatusManager();
    private final ShippingStatusMappingManager shippingStatusMappingManager = new ShippingStatusMappingManager();
    private final TraderDetailsManager traderDetailsManager = new TraderDetailsManager();
    private final TraderManager traderManager = new TraderManager();

    private static DatabaseService instance;

    public static DatabaseService getInstance()  {
        if(instance == null) {
            instance = new DatabaseService();
        }
         return instance;
    }

    public List<Trader> getTrader() throws Exception {
        List<Trader> traders = new ArrayList<Trader>();

        for(TraderDbo traderDbo : this.traderManager.getActiveTraders()) {
            traders.add(this.getTraderDetails(traderDbo));
        }

        return traders;
    }

    public Trader getTrader(int traderId) throws Exception {
        TraderDbo traderDbo = this.traderManager.getById(traderId);
        return this.getTraderDetails(traderDbo);
    }

    private Trader getTraderDetails(TraderDbo traderDbo) throws Exception {
        List<OrderStatus> orderStatusList = new ArrayList<OrderStatus>();

        for(OrderStatusMappingDbo mappingDbo : this.orderStatusMappingManager.getByTraderId(traderDbo.traderId)) {
            OrderStatusDbo dbo = this.orderStatusManager.getById(mappingDbo.orderStatusId);
            orderStatusList.add(OrderStatusMapper.convertToModel(dbo));
        }

        return TraderMapper.convertToModel(traderDbo, orderStatusList);
    }

    public void saveTrader(Trader trader) throws Exception {
        if(trader.getTraderId() > 0) {
            this.traderManager.update(TraderMapper.convertToDbo(trader));

            if(trader instanceof ManualTrader) {
                ManualTrader manualTrader = (ManualTrader)trader;
                this.orderStatusMappingManager.deleteByTraderId(trader.getTraderId());
                for(OrderStatus orderStatus : manualTrader.getOrderStatusList()) {
                    int orderStatusId = orderStatus.getId();

                    if(orderStatusId <= 0) {
                        orderStatusId = this.orderStatusManager.create(OrderStatusMapper.convertToDbo(orderStatus));
                    }
                    else {
                        this.orderStatusManager.update(OrderStatusMapper.convertToDbo(orderStatus));
                    }

                    OrderStatusMappingDbo orderStatusMappingDbo = new OrderStatusMappingDbo();
                    orderStatusMappingDbo.traderId = trader.getTraderId();
                    orderStatusMappingDbo.orderStatusId = orderStatusId;
                    this.orderStatusMappingManager.create(orderStatusMappingDbo);
                }
            }
        }
        else {
            int traderId = this.traderManager.create(TraderMapper.convertToDbo(trader));
            trader.setTraderId(traderId);

            if(trader instanceof ManualTrader) {
                ManualTrader manualTrader = (ManualTrader)trader;
                for(OrderStatus orderStatus : manualTrader.getOrderStatusList()) {
                    int orderStatusId = orderStatus.getId();

                    if(orderStatusId <= 0) {
                        orderStatusId = this.orderStatusManager.create(OrderStatusMapper.convertToDbo(orderStatus));
                    }

                    OrderStatusMappingDbo orderStatusMappingDbo = new OrderStatusMappingDbo();
                    orderStatusMappingDbo.traderId = traderId;
                    orderStatusMappingDbo.orderStatusId = orderStatusId;
                    this.orderStatusMappingManager.create(orderStatusMappingDbo);
                }
            }
        }
    }

    public void updateOrderStatus(Order order, OrderStatus orderStatus) throws Exception {
        this.traderDetailsManager.setStatusId(order.getId(), orderStatus == null ? 0 : orderStatus.getId());
    }

    public void updateOrderIsClosed(Order order, boolean isClosed) throws Exception {
        this.orderManager.setIsClosed(order.getId(), IntBooleanConverter.getInt(isClosed));
    }

    public void saveShipper(Shipper shipper) throws Exception {
        if(shipper.getShipperId() > 0) {
            this.shipperManager.update(ShipperMapper.convertToDbo(shipper));

            if(shipper instanceof ManualShipper) {
                ManualShipper manualShipper = (ManualShipper)shipper;
                this.shippingStatusMappingManager.deleteByShipperId(shipper.getShipperId());
                for(ShippingStatus shippingStatus : manualShipper.getShipperStatusList()) {
                    int shippingStatusId = shippingStatus.getId();

                    if(shippingStatusId <= 0) {
                        shippingStatusId = this.shippingStatusManager.create(ShippingStatusMapper.convertToDbo(shippingStatus));
                    }
                    else {
                        this.shippingStatusManager.update(ShippingStatusMapper.convertToDbo(shippingStatus));
                    }

                    ShippingStatusMappingDbo shippingStatusMappingDbo = new ShippingStatusMappingDbo();
                    shippingStatusMappingDbo.shipperId = shipper.getShipperId();
                    shippingStatusMappingDbo.shippingStatusId = shippingStatusId;
                    this.shippingStatusMappingManager.create(shippingStatusMappingDbo);
                }
            }
        }
        else {
            int shipperId = this.shipperManager.create(ShipperMapper.convertToDbo(shipper));
            shipper.setShipperId(shipperId);

            if(shipper instanceof ManualShipper) {
                ManualShipper manualShipper = (ManualShipper)shipper;
                for(ShippingStatus shippingStatus : manualShipper.getShipperStatusList()) {
                    int shippingStatusId = shippingStatus.getId();

                    if(shippingStatusId <= 0) {
                        shippingStatusId = this.shippingStatusManager.create(ShippingStatusMapper.convertToDbo(shippingStatus));
                    }

                    ShippingStatusMappingDbo shippingStatusMappingDbo = new ShippingStatusMappingDbo();
                    shippingStatusMappingDbo.shipperId = shipperId;
                    shippingStatusMappingDbo.shippingStatusId = shippingStatusId;
                    this.shippingStatusMappingManager.create(shippingStatusMappingDbo);
                }
            }
        }
    }

    public void updateShippingStatus(Order order, ShippingStatus shippingStatus) throws Exception {
        this.shippingDetailsManager.setStatusId(order.getId(), shippingStatus == null ? 0 : shippingStatus.getId());
    }

    public List<Shipper> getShipper() throws Exception {
        List<Shipper> shipper = new ArrayList<Shipper>();

        for(ShipperDbo shipperDbo : this.shipperManager.getActiveShippers()) {
            shipper.add(this.getShipperDetails(shipperDbo));
        }

        return shipper;
    }

    public Shipper getShipper(int shipperId) throws Exception {
        ShipperDbo shipperDbo = this.shipperManager.getById(shipperId);
        return this.getShipperDetails(shipperDbo);
    }

    private Shipper getShipperDetails(ShipperDbo shipperDbo) throws Exception {
        List<ShippingStatus> shippingStatusList = new ArrayList<ShippingStatus>();

        for(ShippingStatusMappingDbo mappingDbo : this.shippingStatusMappingManager.getByShipperId(shipperDbo.shipperId)) {
            ShippingStatusDbo dbo = this.shippingStatusManager.getById(mappingDbo.shippingStatusId);
            shippingStatusList.add(ShippingStatusMapper.convertToModel(dbo));
        }

        return ShipperMapper.convertToModel(shipperDbo, shippingStatusList);
    }

    public void saveOrder(Order order) throws Exception {
        if(order.getId() > 0){
            this.orderManager.update(OrderMapper.convertToDbo(order));
            this.shippingDetailsManager.update(ShippingDetailsMapper.convertToDbo(order));
            this.traderDetailsManager.update(TraderDetailsMapper.convertToDbo(order));
        }
        else {
            int orderId = this.orderManager.create(OrderMapper.convertToDbo(order));
            order.setId(orderId);

            this.shippingDetailsManager.create(ShippingDetailsMapper.convertToDbo(order));
            this.traderDetailsManager.create(TraderDetailsMapper.convertToDbo(order));
        }
    }

    public List<Order> getActiveOrder() throws Exception {
        List<Order> orders = new ArrayList<Order>();

        for(OrderDbo orderDbo : this.orderManager.getActiveOrder()) {
            orders.add(this.getOrderDetails(orderDbo));
        }

        return orders;
    }

    public Order getOrder(int id) throws Exception {
        OrderDbo orderDbo = this.orderManager.getById(id);
        return this.getOrderDetails(orderDbo);
    }

    private Order getOrderDetails(OrderDbo orderDbo) throws Exception {
        if(orderDbo == null) {
            return null;
        }

        TraderDetailsDbo traderDetailsDbo = this.traderDetailsManager.getByOrderId(orderDbo.orderId);
        TraderDbo traderDbo = this.traderManager.getById(traderDetailsDbo.traderId);
        OrderStatus orderStatus = null;
        if(traderDetailsDbo.orderStatusId > 0) {
            orderStatus = OrderStatusMapper.convertToModel(this.orderStatusManager.getById(traderDetailsDbo.orderStatusId));
        }

        ShippingDetailsDbo shippingDetailsDbo = this.shippingDetailsManager.getByOrderId(orderDbo.orderId);
        ShipperDbo shipperDbo = this.shipperManager.getById(shippingDetailsDbo.shipperId);
        ShippingStatus shippingStatus = null;
        if(shippingDetailsDbo.shipperStatusId > 0) {
            shippingStatus = ShippingStatusMapper.convertToModel(this.shippingStatusManager.getById(shippingDetailsDbo.shipperStatusId));
        }

        return OrderMapper.convertToModel(orderDbo, this.getShipperDetails(shipperDbo), shippingDetailsDbo.trackingnumber, shippingStatus, this.getTraderDetails(traderDbo), traderDetailsDbo.ordernumber, orderStatus);
    }
}
