/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.OrderBook.controller;

import com.sg.OrderBook.entities.Trade;
import com.sg.OrderBook.service.TradeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author louie
 */
@Controller
public class TradeController {

    @Autowired
    private TradeService trades;

    @GetMapping("/trades")
    public String TradePage(Model model) {

        List<Trade> allTrades = trades.findAllTrades();

        model.addAttribute("trades", allTrades);

        return "trades";
    }

    @GetMapping("/tradeDetail")
    public String getTrade(Model model, int tradeId) {

        Trade trade = trades.findTradeById(tradeId);

        model.addAttribute("trade", trade);
        model.addAttribute("buyOrder", trade.getBuyorder());
        model.addAttribute("sellOrder", trade.getSellorder());

        return "tradeDetail";
    }

}
