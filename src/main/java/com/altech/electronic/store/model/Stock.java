package com.altech.electronic.store.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import com.altech.electronic.store.exception.InsufficientStockException;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "stocks", uniqueConstraints = @UniqueConstraint(columnNames = { "product_id" }))
public class Stock extends BaseModel {

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity; // Total available stock

    @Column(nullable = false)
    private Integer reservedQuantity = 0; // Items in customer baskets

    @Column(nullable = false)
    private Integer soldQuantity = 0; // Items successfully sold

    // Available quantity is calculated, not stored
    public Integer getAvailableQuantity() {
        return quantity - reservedQuantity - soldQuantity;
    }

    // Called when customer adds to basket
    public void reserve(int amount) {
        if (getAvailableQuantity() < amount) {
            throw new InsufficientStockException(
                    String.format("Only %d items available in stock", getAvailableQuantity()));
        }
        reservedQuantity += amount;
    }

    // Called when customer removes from basket or order is canceled
    public void release(int amount) {
        if (reservedQuantity < amount) {
            throw new IllegalStateException(
                    String.format("Only %d items are reserved", reservedQuantity));
        }
        reservedQuantity -= amount;
    }

    // Called when order is successfully completed
    public void commit(int amount) {
        if (reservedQuantity < amount) {
            throw new IllegalStateException(
                    String.format("Only %d items are reserved", reservedQuantity));
        }
        reservedQuantity -= amount;
        soldQuantity += amount;
    }

    // ADMIN ONLY - for restocking products
    public void restock(int additionalQuantity) {
        if (additionalQuantity <= 0) {
            throw new IllegalArgumentException("Restock quantity must be positive");
        }
        quantity += additionalQuantity;
    }
}