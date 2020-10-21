/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.OrderBook.controller;

import com.sg.OrderBook.entities.OrderTransaction;
import com.sg.OrderBook.entities.Stock;
import com.sg.OrderBook.entities.StockOrder;
import com.sg.OrderBook.service.OrderTransactionService;
import com.sg.OrderBook.service.StockOrderService;
import com.sg.OrderBook.service.StockService;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author louie
 */
@Controller
public class StockOrderController {

    @Autowired
    private StockOrderService orders;

    @Autowired
    private StockService stocks;

    @Autowired
    private OrderTransactionService orderTransactions;

    @PostMapping("/addOrder")
    public String addOrder(@Valid StockOrder stockOrder, BindingResult result, Model model, RedirectAttributes redirectAttributes,
            int stockId) {

        //check for errors
        if (result.hasErrors()) {
            orders.validateStockOrder(stockOrder);

            redirectAttributes.addAttribute("stockId", stockId);
            return "redirect:stockDetail";
        } else {
            //clear errors
            orders.clearStockOrderViolations();
        }

        //set side
        if (stockOrder.getSide().equals("1")) {
            stockOrder.setSide("BUY");
        } else if (stockOrder.getSide().equals("2")) {
            stockOrder.setSide("SELL");
        }

        //get stock
        Stock stock = stocks.findStockById(stockId);

        //add stock to order object
        stockOrder.setStock(stock);

        //set status
        stockOrder.setStatus("IN-PROGRESS");

        //add time
        setTimeOfTransaction(stockOrder);

        //save order
        orders.saveOrder(stockOrder);

        //set to order transaction
        OrderTransaction orderTransaction = setOrderTransaction("CREATED", stockOrder);

        //save transaction
        orderTransactions.saveOrderTransaction(orderTransaction);

        redirectAttributes.addAttribute("stockId", stockId);
        return "redirect:stockDetail";
    }

    @GetMapping("/orderDetail")
    public String displayOrderDetail(Model model, int orderId) {

        //find order
        StockOrder stockOrder = orders.findOrderById(orderId);

        //get all transactions for an order
        List<OrderTransaction> allOrderTransactions = orderTransactions.findByStockOrder(stockOrder);

        model.addAttribute("order", stockOrder);
        model.addAttribute("transactions", allOrderTransactions);

        return "orderDetail";
    }

    @GetMapping("/cancelOrder")
    public String cancelOrder(Model model, int orderId, int stockId, RedirectAttributes redirectAttributes) {

        //find order
        StockOrder stockOrder = orders.findOrderById(orderId);

        //update order
        stockOrder.setStatus("CANCELLED");

        //save order
        orders.saveOrder(stockOrder);

        //set time of deletion
        setTimeOfTransaction(stockOrder);

        //set to order transaction
        OrderTransaction orderTransaction = setOrderTransaction("CANCELLED", stockOrder);

        //save transaction
        orderTransactions.saveOrderTransaction(orderTransaction);

        // redirect to stock details
        redirectAttributes.addAttribute("stockId", stockId);

        return "redirect:stockDetail";
    }

    @GetMapping("/orders")
    public String displayAllOrders(Model model, int stockId, String side) {

        //find all orders by stock and side
        List<StockOrder> allOrders = orders.findByStockIdAndSide(stockId, side);

        model.addAttribute("orders", allOrders);

        return "orders";
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
