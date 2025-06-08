package com.altech.electronic.store.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.altech.electronic.store.enums.DiscountTypeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_discounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDiscount extends BaseModel {
	
    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "discount_id")
    @JoinColumn(name = "discount_id", referencedColumnName = "id")
    private Discount discount;
    
    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id")
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
    
    @Column(nullable = false)
    @Builder.Default
    private Boolean isActive = true;
    
    @Column(nullable = false)
    private LocalDateTime validFrom;
    
    private LocalDateTime validTo;
}