package com.altech.electronic.store.service;

import org.springframework.security.access.prepost.PreAuthorize;

import com.altech.electronic.store.model.Stock;

public interface StockService {

	Stock getStockByProductId(Long productId);
    
    // For customers
    void reserveStock(Long productId, int quantity);
    void releaseStock(Long productId, int quantity);
    void commitStock(Long productId, int quantity);
    
    // For admin only
    @PreAuthorize("hasRole('ADMIN')")
    void restock(Long productId, int additionalQuantity);
    
    // View methods
    Integer getAvailableQuantity(Long productId);
    Integer getReservedQuantity(Long productId);
    Integer getSoldQuantity(Long productId);
    
}
