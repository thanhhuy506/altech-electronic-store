package com.altech.electronic.store.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.altech.electronic.store.dto.DiscountDTO;
import com.altech.electronic.store.dto.ProductDiscountReqDTO;
import com.altech.electronic.store.dto.ProductDiscountRespDTO;
import com.altech.electronic.store.model.Discount;
import com.altech.electronic.store.model.ProductDiscount;

public class ProductDiscounttMapper {
	
	public static ProductDiscount toEntity(ProductDiscountReqDTO productDiscountReqDTO) {
        return ProductDiscount.builder()
        		.isActive(productDiscountReqDTO.getIsActive())
        		.validFrom(productDiscountReqDTO.getValidFrom())
        		.validTo(productDiscountReqDTO.getValidTo())
                .build();
    }
	
	public static ProductDiscountRespDTO toDTO(ProductDiscount productDiscount) {
        return ProductDiscountRespDTO.builder()
        		.id(productDiscount.getId())
                .discountId(productDiscount.getDiscount().getId())
                .type(productDiscount.getDiscount().getType())
                .description(productDiscount.getDiscount().getDescription())
                .productId(productDiscount.getProduct().getId())
                .isActive(productDiscount.getIsActive())
                .validFrom(productDiscount.getValidFrom())
                .validTo(productDiscount.getValidTo())
                .discount(ProductDiscounttMapper.toDiscountDTO(productDiscount.getDiscount()))
                .build();
    }
	
	public static DiscountDTO toDiscountDTO(Discount discount) {
        return DiscountDTO.builder()
        		.requiredQuantity(discount.getRequiredQuantity())
        	    .discountedQuantity(discount.getDiscountedQuantity())
        	    .discountValue(discount.getDiscountValue())
                .build();
    }
	
	public static List<ProductDiscountRespDTO> toDTOs(List<ProductDiscount> productDiscounts) {
	    return productDiscounts.stream()
	            .map(ProductDiscounttMapper::toDTO)
	            .collect(Collectors.toList());
	}
}
