package com.altech.electronic.store.service;

import com.altech.electronic.store.model.ProductDiscount;

import dto.ProductDiscountReqDTO;
import dto.ProductDiscountRespDTO;

public interface ProductDiscountService extends BaseService<ProductDiscount>{
	
	ProductDiscountRespDTO addProductDiscount(ProductDiscountReqDTO productDiscountReqDTO);
	
	ProductDiscountRespDTO getByProductId(Long productId);

}
