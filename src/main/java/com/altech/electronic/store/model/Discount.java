package com.altech.electronic.store.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.altech.electronic.store.enums.DiscountTypeEnum;

@Entity
@Table(name = "discounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Discount extends BaseModel {
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DiscountTypeEnum type;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
 // For quantity-based deals
    @Column(nullable = false)
    private Integer requiredQuantity;
    @Column(nullable = false)
    private Integer discountedQuantity;
    @Column(nullable = false)
    private BigDecimal discountValue;

}