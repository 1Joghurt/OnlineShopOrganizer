package com.project.organizer.database;

import com.project.organizer.model.Order;
import com.project.organizer.model.shipper.ManualShipper;
import com.project.organizer.model.shipper.Shipper;
import com.project.organizer.model.shipper.ShippingStatus;
import com.project.organizer.model.trader.ManualTrader;
import com.project.organizer.model.trader.OrderStatus;
import com.project.organizer.model.trader.Trader;
import com.project.organizer.model.trader.managedTrader.amazonManagedTrader;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DatabaseTest {

    public static void createTestdaten() {
        try {
            List<Trader> newTraders = new ArrayList<Trader>();

            //Trader Amazon
            /*
            List<OrderStatus> orderStatusListAmazon = new ArrayList<OrderStatus>();
            orderStatusListAmazon.add(new OrderStatus(0, "Bestellt", false));
            orderStatusListAmazon.add(new OrderStatus(0, "Versandt", false));
            orderStatusListAmazon.add(new OrderStatus(0, "In Zustellung", false));
            orderStatusListAmazon.add(new OrderStatus(0, "Zugestellt", true));
            Trader traderAmazon = new ManualTrader(0, "Amazon", "", orderStatusListAmazon);
            newTraders.add(traderAmazon);
            */
            Trader traderAmazon = new amazonManagedTrader(0, "Amazon", "", true, true, "", "", "");
            newTraders.add(traderAmazon);

            //Trader eBay
            List<OrderStatus> orderStatusListeBay = new ArrayList<OrderStatus>();
            orderStatusListeBay.add(new OrderStatus(0, "Bestellt", false));
            orderStatusListeBay.add(new OrderStatus(0, "Versandt", false));
            orderStatusListeBay.add(new OrderStatus(0, "In Zustellung", false));
            orderStatusListeBay.add(new OrderStatus(0, "Zuges3tellt", true));
            Trader tradereBay = new ManualTrader(0, "eBay", "eBay@eBay.com", orderStatusListeBay);
            newTraders.add(tradereBay);

            //Trader Check24.de
            List<OrderStatus> orderStatusListCheck24 = new ArrayList<OrderStatus>();
            orderStatusListCheck24.add(new OrderStatus(0, "Best3ellt", false));
            orderStatusListCheck24.add(new OrderStatus(0, "Vers3andt", false));
            orderStatusListCheck24.add(new OrderStatus(0, "In Z2333ustellung", false));
            orderStatusListCheck24.add(new OrderStatus(0, "Zugestellt", true));
            Trader traderCheck24 = new ManualTrader(0, "Check24.de", "", orderStatusListCheck24);
            newTraders.add(traderCheck24);

            //Trader Sport-Shop.de
            List<OrderStatus> orderStatusListSportShop = new ArrayList<OrderStatus>();
            orderStatusListSportShop.add(new OrderStatus(0, "Bestellt", false));
            orderStatusListSportShop.add(new OrderStatus(0, "Versandt", false));
            orderStatusListSportShop.add(new OrderStatus(0, "In Zustellung", false));
            orderStatusListSportShop.add(new OrderStatus(0, "Zugestellt", true));
            Trader traderSportShop = new ManualTrader(0, "Sport-Shop.de", "", orderStatusListSportShop);
            newTraders.add(traderSportShop);

            for(Trader trader : newTraders) {
                DatabaseService.getInstance().saveTrader(trader);
            }

            List<Shipper> newShippers = new ArrayList<Shipper>();

            //DHL
            List<ShippingStatus> shippingStatusListDhl = new ArrayList<ShippingStatus>();
            shippingStatusListDhl.add(new ShippingStatus(0, "Abgabe / Abholung"));
            shippingStatusListDhl.add(new ShippingStatus(0, "Transport Logistikzentrum"));
            shippingStatusListDhl.add(new ShippingStatus(0, "Transport Verteilzentrum"));
            shippingStatusListDhl.add(new ShippingStatus(0, "In Zustellung"));
            shippingStatusListDhl.add(new ShippingStatus(0, "Zugestellt"));
            Shipper shipperDhl = new ManualShipper(0, "DHL", shippingStatusListDhl);
            newShippers.add(shipperDhl);

            //UPS
            List<ShippingStatus> shippingStatusListUps = new ArrayList<ShippingStatus>();
            shippingStatusListUps.add(new ShippingStatus(0, "Abgabe / Abholung"));
            shippingStatusListUps.add(new ShippingStatus(0, "Transport zum Logißßstikzentrum"));
            shippingStatusListUps.add(new ShippingStatus(0, "Transport zum Verteilzentrum"));
            shippingStatusListUps.add(new ShippingStatus(0, "In Zustellung"));
            shippingStatusListUps.add(new ShippingStatus(0, "Zugestellt"));
            Shipper shipperUps = new ManualShipper(0, "UPS", shippingStatusListUps);
            newShippers.add(shipperUps);

            //Hermes
            List<ShippingStatus> shippingStatusListHermes = new ArrayList<ShippingStatus>();
            shippingStatusListHermes.add(new ShippingStatus(0, "Abgabe / Abholung"));
            shippingStatusListHermes.add(new ShippingStatus(0, "Transport zum Logistikzentrum"));
            shippingStatusListHermes.add(new ShippingStatus(0, "Transport zum Verteilzentrum"));
            shippingStatusListHermes.add(new ShippingStatus(0, "In Zustellung"));
            shippingStatusListHermes.add(new ShippingStatus(0, "Zugestellt"));
            Shipper shipperHermes = new ManualShipper(0, "Hermes", shippingStatusListHermes);
            newShippers.add(shipperHermes);

            for(Shipper shipper : newShippers) {
                DatabaseService.getInstance().saveShipper(shipper);
            }

            List<Order> newOrders = new ArrayList<Order>();

            newOrders.add(new Order(0, false, "Tischtennisschläger", LocalDateTime.now(), shipperDhl,
                    "123", null, traderAmazon, "456", null));

            newOrders.add(new Order(0, false, "Tischtennisbälle 5 Stk.", LocalDateTime.now(), shipperHermes,
                    "654", null, tradereBay, "987", null));

            newOrders.add(new Order(0, false, "Fußball", LocalDateTime.now(), shipperDhl,
                    "741", null, traderCheck24, "852", null));

            newOrders.add(new Order(0, false, "Schienbeinschoner", LocalDateTime.now(), shipperUps,
                    "258", null, traderSportShop, "369", null));

            for(Order order : newOrders) {
                DatabaseService.getInstance().saveOrder(order);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testeDatenbank() {
        int i = 2;
        boolean addNewItems = true;
        boolean pickLast = true;
        try {
            if(addNewItems) {
                List<OrderStatus> orderStatusList = new ArrayList<OrderStatus>();

                orderStatusList.add(new OrderStatus(0, "Bestellt", false));
                orderStatusList.add(new OrderStatus(0, "Verschickt", false));
                orderStatusList.add(new OrderStatus(0, "Abgeschlossen", true));

                Trader newTrader = new ManualTrader(0, "Händler " + i, "", orderStatusList);
                DatabaseService.getInstance().saveTrader(newTrader);
            }

            List<Trader> allTrader = DatabaseService.getInstance().getTrader();

            if(addNewItems) {
                List<ShippingStatus> shippingStatusList = new ArrayList<ShippingStatus>();

                shippingStatusList.add(new ShippingStatus(0, "Daten erhalten"));
                shippingStatusList.add(new ShippingStatus(0, "Unterwegs"));
                shippingStatusList.add(new ShippingStatus(0, "Zugestellt"));

                Shipper newShipper = new ManualShipper(0, "Lieferant " + i, shippingStatusList);

                DatabaseService.getInstance().saveShipper(newShipper);
            }

            List<Shipper> allShipper = DatabaseService.getInstance().getShipper();

            if(addNewItems) {
                Trader trader = null;
                Shipper shipper = null;

                if(allTrader.size() > 0 && allShipper.size() > 0) {
                    if(pickLast) {
                        trader = allTrader.get(allTrader.size() - 1);
                        shipper = allShipper.get(allShipper.size() - 1);
                    }
                    else {
                        trader = allTrader.get(0);
                        shipper = allShipper.get(0);
                    }
                }

                Order order = new Order(0, false, "Test " + i, LocalDateTime.now(), shipper, "trackingnumber", null, trader, "ordernumber", null);

                DatabaseService.getInstance().saveOrder(order);
            }

            List<Order> allOrders = DatabaseService.getInstance().getActiveOrder();

            int orderId = 0;

            if(allOrders.size() > 0) {
                if(pickLast) {
                    orderId = allOrders.get(allOrders.size() - 1).getId();
                }
                else {
                    orderId = allOrders.get(0).getId();
                }
            }

            Order order = DatabaseService.getInstance().getOrder(orderId);

            if(order != null) {
                OrderStatus orderStatus = null;
                if(order.getTrader() instanceof  ManualTrader) {
                    ManualTrader manualTrader = (ManualTrader)order.getTrader();
                    if(manualTrader.getOrderStatusList().size() > 0) {
                        if(pickLast) {
                            orderStatus = manualTrader.getOrderStatusList().get(manualTrader.getOrderStatusList().size() - 1);
                        }
                        else {
                            orderStatus = manualTrader.getOrderStatusList().get(0);
                        }
                    }
                }

                if(orderStatus != null) {
                    DatabaseService.getInstance().updateOrderStatus(order, orderStatus);
                }

                ShippingStatus shippingStatus = null;
                if(order.getShipper() instanceof  ManualShipper) {
                    ManualShipper manualShipper = (ManualShipper)order.getShipper();
                    if(manualShipper.getShipperStatusList().size() > 0) {
                        if(pickLast) {
                            shippingStatus = manualShipper.getShipperStatusList().get(manualShipper.getShipperStatusList().size() - 1);
                        }
                        else {
                            shippingStatus = manualShipper.getShipperStatusList().get(0);
                        }
                    }
                }

                if(shippingStatus != null) {
                    DatabaseService.getInstance().updateShippingStatus(order, shippingStatus);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
