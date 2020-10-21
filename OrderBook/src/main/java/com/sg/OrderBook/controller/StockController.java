/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.OrderBook.controller;

import com.sg.OrderBook.entities.Stock;
import com.sg.OrderBook.entities.StockOrder;
import com.sg.OrderBook.entities.Trade;
import com.sg.OrderBook.service.StockOrderService;
import com.sg.OrderBook.service.StockService;
import com.sg.OrderBook.service.TradeService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author anmol
 */
@Controller
public class StockController {

    @Autowired
    private StockService stocks;

    @Autowired
    private StockOrderService orders;

    @Autowired
    private TradeService trades;

    @PostMapping("/addStock")
    public String addStock(@Valid Stock stock, BindingResult result, Model model) {

        //check for errors
        if (result.hasErrors()) {
            stocks.validateStock(stock);
            return "redirect:stocks";
        } else {
            //clear errors
            stocks.clearStockViolations();
        }

        //save stock
        stocks.saveStock(stock);

        return "redirect:stocks";
    }

    @GetMapping("/stocks")
    public String displayStocks(Model model) {

        List<Stock> allStocks = stocks.findAllStock();

        model.addAttribute("stocks", allStocks);

        //clear stock order errors
        orders.clearStockOrderViolations();
        //return errors
        model.addAttribute("errors", stocks.getStockViolations());

        return "stocks";
    }

    @GetMapping("/stockDetail")
    public String displayStockDetails(Model model, int stockId) {

        //find stock
        Stock stock = stocks.findStockById(stockId);

        //make trade for specific stock
        trades.makeStockTrade(stock);

        //get orders of stock
        List<StockOrder> buyOrder = orders.findOrdersByStockIdAndSideAndpriceDesc(stock, "BUY", "IN-PROGRESS");

        List<StockOrder> sellOrder = orders.findOrdersByStockIdAndSideAndpriceAsc(stock, "SELL", "IN-PROGRESS");

        List<Trade> stockTrades = trades.findTradeByStock(stock);

        stocks.clearStockViolations();
        //return errors
        model.addAttribute("errors", orders.getStockOrderViolations());

        model.addAttribute("buyOrders", buyOrder);
        model.addAttribute("sellOrders", sellOrder);
        model.addAttribute("trades", stockTrades);
        model.addAttribute("stock", stock);

        return "stockDetail";
    }

}
