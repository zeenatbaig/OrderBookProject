/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.OrderBook.service;

import com.sg.OrderBook.entities.Stock;
import com.sg.OrderBook.entities.StockOrder;
import com.sg.OrderBook.repositories.StockOrderRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author zeenatbaig
 */
@Service
public class StockOrderService {

    @Autowired
    private StockOrderRepository orders;

    private Set<ConstraintViolation<StockOrder>> stockOrderViolations = new HashSet<>();

    private Validator validate = Validation.buildDefaultValidatorFactory().getValidator();

    public void saveOrder(StockOrder order) {
        orders.save(order);
    }

    public List<StockOrder> findAllOrders() {
        return orders.findAll();
    }

    public List<StockOrder> findOrdersByStockId(Stock stock) {
        return orders.findByStockId(stock.getId());
    }

    public List<StockOrder> findOrdersByStockIdAndSideAndpriceAsc(Stock stock, String side, String status) {
        return orders.findByStockIdAndSideAndStatusOrderByPriceAsc(stock.getId(), side, status);
    }

    public List<StockOrder> findOrdersByStockIdAndSideAndpriceDesc(Stock stock, String side, String status) {
        return orders.findByStockIdAndSideAndStatusOrderByPriceDesc(stock.getId(), side, status);
    }

    public List<StockOrder> findOrderByDatetimeOrderByDatetimeAsc(java.sql.Timestamp datetime) {
        return orders.findByDatetimeOrderByDatetimeAsc(datetime);
    }

    List<StockOrder> findByStockAndSideOrderByDatetimeAsc(Stock stock, String side, String status) {
        return orders.findByStockAndSideAndStatusOrderByDatetimeAsc(stock, side, status);
    }

    List<StockOrder> findByStockAndStatusOrderByDatetimeAsc(Stock stock, String status) {
        return orders.findByStockAndStatusOrderByDatetimeAsc(stock, status);
    }

    public List<StockOrder> findOrderBySide(String side) {
        return orders.findBySide(side);
    }

    public List<StockOrder> findOrderByStatus(String status) {
        return orders.findByStatus(status);
    }

    public List<StockOrder> findOrderByQuantity(int quantity) {
        return orders.findByQuantity(quantity);
    }

    public StockOrder findOrderById(int id) {
        return orders.findById(id).orElse(null);
    }

    public void deleteOrderById(int id) {
        orders.deleteById(id);
    }

    public List<StockOrder> findByStockIdAndSide(int stockId, String side) {
        return orders.findByStockIdAndSide(stockId, side);
    }

    public Set<ConstraintViolation<StockOrder>> validateStockOrder(StockOrder stockOrder) {
        return stockOrderViolations = validate.validate(stockOrder);
    }

    public Set<ConstraintViolation<StockOrder>> getStockOrderViolations() {
        return stockOrderViolations;
    }

    public void clearStockOrderViolations() {

        stockOrderViolations.clear();

    }

}
