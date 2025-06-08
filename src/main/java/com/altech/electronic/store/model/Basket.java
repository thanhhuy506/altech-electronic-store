package com.altech.electronic.store.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "baskets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Basket extends BaseModel {

    private Long customerId;

    @ElementCollection
    @CollectionTable(name = "basket_items", joinColumns = @JoinColumn(name = "basket_id"))
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<Long, Integer> items = new HashMap<>();
    
    public Basket(Long customerId) {
        this.customerId = customerId;
    }
    
    public void addItem(Long productId, int quantity) {
        items.merge(productId, quantity, Integer::sum);
    }
    
    public void removeItem(Long productId, int quantity) {
        items.computeIfPresent(productId, (k, v) -> v > quantity ? v - quantity : null);
    }
    
    public void clear() {
        items.clear();
    }
    
}