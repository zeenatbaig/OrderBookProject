/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.OrderBook.repositories;

import com.sg.OrderBook.entities.Stock;
import com.sg.OrderBook.entities.StockOrder;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author anmol
 */
@Repository
public interface StockOrderRepository extends JpaRepository<StockOrder, Integer> {

    List<StockOrder> findByDatetimeOrderByDatetimeAsc(java.sql.Timestamp datetime);

    List<StockOrder> findByStockId(int stockId);

    List<StockOrder> findBySide(String side);

    List<StockOrder> findByStatus(String status);

    List<StockOrder> findByQuantity(int quantity);

    List<StockOrder> findByStockIdAndSideAndStatusOrderByPriceAsc(int stockId, String side, String status);

    List<StockOrder> findByStockIdAndSideAndStatusOrderByPriceDesc(int stockId, String side, String status);

    List<StockOrder> findByStockAndSideAndStatusOrderByDatetimeAsc(Stock stock, String side,  String status);

    List<StockOrder> findByStockAndStatusOrderByDatetimeAsc(Stock stock, String status);

    List<StockOrder> findByStockIdAndSide(int stockId, String side);

}
