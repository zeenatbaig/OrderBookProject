/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.OrderBook.repositories;

import com.sg.OrderBook.entities.StockOrder;
import com.sg.OrderBook.entities.Trade;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author anmol
 */
@Repository
public interface TradeRepository extends JpaRepository<Trade, Integer> {

    List<Trade> findByBuyorder(StockOrder order);

    List<Trade> findBySellorder(StockOrder order);

    List<Trade> findByStockIdOrderByDatetimeAsc(int stockId);
}
