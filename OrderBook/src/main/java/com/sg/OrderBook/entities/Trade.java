/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.OrderBook.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author anmol
 */
@Entity
public class Trade {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column
    private int quantity;

    @Column
    private java.sql.Timestamp datetime;

    @Column
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "stockId", nullable = false)
    private Stock stock;

    @ManyToOne
    @JoinColumn(name = "buyOrderId", nullable = false)
    private StockOrder buyorder;

    @ManyToOne
    @JoinColumn(name = "sellOrderId", nullable = false)
    private StockOrder sellorder;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public StockOrder getBuyorder() {
        return buyorder;
    }

    public void setBuyorder(StockOrder buyorder) {
        this.buyorder = buyorder;
    }

    public StockOrder getSellorder() {
        return sellorder;
    }

    public void setSellorder(StockOrder sellorder) {
        this.sellorder = sellorder;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.id;
        hash = 53 * hash + this.quantity;
        hash = 53 * hash + Objects.hashCode(this.datetime);
        hash = 53 * hash + Objects.hashCode(this.price);
        hash = 53 * hash + Objects.hashCode(this.stock);
        hash = 53 * hash + Objects.hashCode(this.buyorder);
        hash = 53 * hash + Objects.hashCode(this.sellorder);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Trade other = (Trade) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.quantity != other.quantity) {
            return false;
        }
        if (!Objects.equals(this.datetime, other.datetime)) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        if (!Objects.equals(this.stock, other.stock)) {
            return false;
        }
        if (!Objects.equals(this.buyorder, other.buyorder)) {
            return false;
        }
        if (!Objects.equals(this.sellorder, other.sellorder)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Trade{" + "id=" + id + ", quantity=" + quantity + ", datetime=" + datetime + ", price=" + price + ", stock=" + stock + ", buyorder=" + buyorder + ", sellorder=" + sellorder + '}';
    }

}
