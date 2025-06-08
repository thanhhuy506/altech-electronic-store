package com.altech.electronic.store.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import dto.BasketItemRespDTO;
import dto.BasketRespDTO;
import dto.DiscountDTO;

public class CalculatePrice extends BaseUtils {
	
	public static BasketItemRespDTO calculatePrice(BasketItemRespDTO dto, BigDecimal unitPrice, DiscountDTO discount) {
	    Integer quantity = dto.getQuantity();
	    BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));

	    BigDecimal totalDiscountPrice = BigDecimal.ZERO;

	    if (discount != null &&
	        discount.getRequiredQuantity() != null &&
	        discount.getDiscountedQuantity() != null &&
	        discount.getDiscountValue() != null &&
	        quantity >= discount.getRequiredQuantity()) {
	    	
	    	int remainingDiscountQuantity = quantity - discount.getRequiredQuantity();
	    	int discountQuantity = discount.getRequiredQuantity();
	    	
	    	if (remainingDiscountQuantity < discount.getRequiredQuantity()) {
	    		discountQuantity = remainingDiscountQuantity;
	    	}

	        totalDiscountPrice = discount.getDiscountValue()
	                .multiply(unitPrice.multiply(BigDecimal.valueOf(discountQuantity)))
	                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP); 

	    }

	    BigDecimal finalPrice = totalPrice.subtract(totalDiscountPrice);

	    dto.setTotalPrice(totalPrice);
	    dto.setTotalDiscountPrice(totalDiscountPrice);
	    dto.setFinalPrice(finalPrice);

	    return dto;
	}
	
	public static BasketRespDTO calculateBasketPrice(BasketRespDTO basket) {
	    BigDecimal totalPrice = BigDecimal.ZERO;
	    BigDecimal totalDiscountPrice = BigDecimal.ZERO;
	    BigDecimal finalPrice = BigDecimal.ZERO;

	    for (BasketItemRespDTO item : basket.getItems()) {
	        if (item.getTotalPrice() != null) {
	            totalPrice = totalPrice.add(item.getTotalPrice());
	        }
	        if (item.getTotalDiscountPrice() != null) {
	            totalDiscountPrice = totalDiscountPrice.add(item.getTotalDiscountPrice());
	        }
	        if (item.getFinalPrice() != null) {
	            finalPrice = finalPrice.add(item.getFinalPrice());
	        }
	    }

	    basket.setTotalPrice(totalPrice);
	    basket.setTotalDiscountPrice(totalDiscountPrice);
	    basket.setFinalPrice(finalPrice);

	    return basket;
	}
}
