/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.OrderBook.repositories;

import com.sg.OrderBook.entities.OrderTransaction;
import com.sg.OrderBook.entities.StockOrder;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author louie
 */
@Repository
public interface OrderTransactionRepository extends JpaRepository<OrderTransaction, Integer> {

    List<OrderTransaction> findByStockOrder(StockOrder stockOrder);

}
