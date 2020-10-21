/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.OrderBook.service;

import com.sg.OrderBook.entities.OrderTransaction;
import com.sg.OrderBook.entities.StockOrder;
import com.sg.OrderBook.repositories.OrderTransactionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author louie
 */
@Service
public class OrderTransactionService {

    @Autowired
    OrderTransactionRepository orderTransactions;

    public List<OrderTransaction> findByStockOrder(StockOrder stockOrder) {

        return orderTransactions.findByStockOrder(stockOrder);

    }

    public void saveOrderTransaction(OrderTransaction orderTransaction) {

        orderTransactions.save(orderTransaction);

    }

}
