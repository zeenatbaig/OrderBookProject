/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.OrderBook.service;

import com.sg.OrderBook.entities.OrderTransaction;
import com.sg.OrderBook.entities.Stock;
import com.sg.OrderBook.entities.StockOrder;
import com.sg.OrderBook.entities.Trade;
import com.sg.OrderBook.repositories.TradeRepository;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author anmol
 */
@Service
public class TradeService {

    @Autowired
    private TradeRepository trades;

    @Autowired
    private StockOrderService orders;

    @Autowired
    private OrderTransactionService orderTransactions;

    public List<Trade> findAllTrades() {

        return trades.findAll();

    }

    public void makeTrade(Trade trade) {
        trades.save(trade);
    }

    public Trade findTradeById(int id) {

        return trades.findById(id).orElse(null);

    }

    public void deleteTradeById(int id) {

        trades.deleteById(id);

    }

    public List<Trade> findTradeByBuyorder(StockOrder order) {
        return trades.findByBuyorder(order);
    }

    public List<Trade> findTradeBySellorder(StockOrder order) {

        return trades.findBySellorder(order);
    }

    public List<Trade> findTradeByStock(Stock stock) {
        return trades.findByStockIdOrderByDatetimeAsc(stock.getId());
    }

    public void saveTrade(Trade trade) {
        trades.save(trade);
    }

    public void makeStockTrade(Stock stock) {
        Trade trade = new Trade();
        boolean tradeMade = false;
        
            
        List<StockOrder> sellOrders = orders.findByStockAndSideOrderByDatetimeAsc(stock, "SELL", "IN-PROGRESS");

        List<StockOrder> inProgressOrders = orders.findByStockAndStatusOrderByDatetimeAsc(stock, "IN-PROGRESS");

        for (StockOrder order : inProgressOrders) {
            String side = order.getSide();

            if (side.equals("BUY")) {
                makeTradeBUY(order, sellOrders);

            }
        }
        

    }

    private void makeTradeBUY(StockOrder buyOrder, List<StockOrder> sellOrders) {
        Trade trade = new Trade();
        int updatedBuyQuantity = 0;
        int updatedSellQuantity = 0;

        BigDecimal buyPrice = buyOrder.getPrice();
        int buyQuantity = buyOrder.getQuantity();
        Stock stock = buyOrder.getStock();

        //going through all the sell orders
        for (StockOrder sellOrder : sellOrders) {

            // information on the sell order
            BigDecimal sellPrice = sellOrder.getPrice();
            int sellQuantity = sellOrder.getQuantity();

            //matching the price
            if (sellPrice.compareTo(buyPrice) <= 0 ) {

                if (sellOrder.getQuantity() != 0 && buyOrder.getQuantity()!= 0) {
                    //validating the quantity
                    if (buyQuantity <= sellQuantity) {

                        //getting the quantity for the trade
                        updatedSellQuantity = sellQuantity - buyQuantity;
                        
                        
                        buyQuantity = 0;
                        sellQuantity = updatedSellQuantity;

                        //updating the quantity for each order
                        sellOrder.setQuantity(updatedSellQuantity);
                        buyOrder.setQuantity(0);

                        //updates status
                        buyOrder.setStatus("COMPLETED");

                        setTimeOfTransaction(buyOrder);

                        OrderTransaction orderTransaction = setOrderTransaction("FULFILLED", buyOrder);

                        //save transaction
                        orderTransactions.saveOrderTransaction(orderTransaction);

                        if (buyQuantity == sellQuantity) {
                            sellOrder.setStatus("COMPLETED");

                            setTimeOfTransaction(sellOrder);
                            OrderTransaction sellOrderTransaction = setOrderTransaction("FULFILLED", sellOrder);
                           
                            
                            orderTransactions.saveOrderTransaction(sellOrderTransaction);

                        } else {
                            OrderTransaction sellOrderTransaction = setOrderTransaction("PARTIALLY-FULFILLED", sellOrder);

                            //save transaction
                            orderTransactions.saveOrderTransaction(sellOrderTransaction);
                        }

                        //saves it to SQL
                        orders.saveOrder(buyOrder);
                        orders.saveOrder(sellOrder);

                        //creating the trade and populating the object
                        trade.setBuyorder(buyOrder);
                        trade.setSellorder(sellOrder);
                        trade.setPrice(sellPrice);
                        trade.setQuantity(sellQuantity);
                        trade.setStock(stock);

                        //sets the date and time for trade
                        Date date = new Date();
                        Timestamp ts = new Timestamp(date.getTime());
                        trade.setDatetime(ts);

                        saveTrade(trade);
                        
                        
                        
                        

                    } else if (sellQuantity < buyQuantity) //getting the quantity for the trade
                    {
                        updatedBuyQuantity = buyQuantity - sellQuantity;
                        
                        
                        buyQuantity = updatedBuyQuantity;
                        sellQuantity = 0;

                        //updating the quantity for each order
                        sellOrder.setQuantity(0);
                        buyOrder.setQuantity(updatedBuyQuantity);

                        //updates status
                        sellOrder.setStatus("COMPLETED");

                        //creates Order transaction for the sell order
                        setTimeOfTransaction(sellOrder);
                        OrderTransaction sellOrderTransaction = setOrderTransaction("FULFILLED", sellOrder);
                        orderTransactions.saveOrderTransaction(sellOrderTransaction);

                        //creates Order transaction for the buy order
                        setTimeOfTransaction(buyOrder);
                        OrderTransaction orderTransaction = setOrderTransaction("PARTIALLY-FULFILLED", buyOrder);
                        orderTransactions.saveOrderTransaction(orderTransaction);

                        //saves it to SQL
                        orders.saveOrder(buyOrder);
                        orders.saveOrder(sellOrder);

                        //creating the trade and populating the object
                        trade.setBuyorder(buyOrder);
                        trade.setSellorder(sellOrder);
                        trade.setPrice(sellPrice);
                        trade.setQuantity(buyQuantity);
                        trade.setStock(stock);

                        //sets the date and time for trade
                        Date date = new Date();
                        Timestamp ts = new Timestamp(date.getTime());
                        trade.setDatetime(ts);
                        saveTrade(trade);
                    }
                }
            }

        }

    }

    private void makeTradeSELL(StockOrder order, List<StockOrder> buyOrders) {
        Trade trade = new Trade();
        int updatedBuyQuantity = 0;
        int updatedSellQuantity = 0;

        BigDecimal price = order.getPrice();
        int quantity = order.getQuantity();
        Stock stock = order.getStock();

        for (StockOrder buyOrder : buyOrders) {

            // information on the buy order
            BigDecimal buyPrice = buyOrder.getPrice();
            int buyQuantity = buyOrder.getQuantity();

            //matching the price
            if (buyPrice.compareTo(price) >= 0) {

                //validating the quantity
                if (quantity <= buyQuantity) {

                    //getting the quantity for the trade
                    updatedBuyQuantity = buyQuantity - quantity;

                    //updating the quantity for each order
                    buyOrder.setQuantity(updatedBuyQuantity);
                    order.setQuantity(0);

                    //updates status
                    order.setStatus("COMPLETED");
                    if (quantity == buyQuantity) {
                        buyOrder.setStatus("COMPLETED");
                    }

                    //saves to SQL
                    orders.saveOrder(order);
                    orders.saveOrder(buyOrder);

                    //creating the trade and populating the object
                    trade.setSellorder(order);
                    trade.setBuyorder(buyOrder);
                    trade.setPrice(buyPrice);
                    trade.setQuantity(quantity);
                    trade.setStock(stock);
                    //sets the date and time for trade
                    Date date = new Date();
                    Timestamp ts = new Timestamp(date.getTime());
                    trade.setDatetime(ts);
                    saveTrade(trade);

                } else if (buyQuantity < quantity) //getting the quantity for the trade
                {
                    updatedSellQuantity = quantity - buyQuantity;

                    //updating the quantity for each order
                    buyOrder.setQuantity(0);
                    order.setQuantity(updatedSellQuantity);

                    //sets status
                    buyOrder.setStatus("COMPLETED");

                    //creating the trade and populating the object
                    trade.setSellorder(order);
                    trade.setBuyorder(buyOrder);
                    trade.setPrice(buyPrice);
                    trade.setQuantity(buyQuantity);
                    trade.setStock(stock);

                    Date date = new Date();
                    Timestamp ts = new Timestamp(date.getTime());
                    trade.setDatetime(ts);
                    saveTrade(trade);
                }
            }

        }

    }

    private OrderTransaction setOrderTransaction(String status, StockOrder stockOrder) {

        OrderTransaction orderTransaction = new OrderTransaction();
        orderTransaction.setQuantity(stockOrder.getQuantity());
        orderTransaction.setDatetime(stockOrder.getDatetime());
        orderTransaction.setStockOrder(stockOrder);
        orderTransaction.setTransactiontype(status);

        return orderTransaction;
    }

    private void setTimeOfTransaction(StockOrder stockOrder) {

        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());
        stockOrder.setDatetime(ts);

    }
}
