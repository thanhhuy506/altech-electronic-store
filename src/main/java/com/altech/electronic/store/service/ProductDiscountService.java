package com.altech.electronic.store.service;

import com.altech.electronic.store.dto.ProductDiscountReqDTO;
import com.altech.electronic.store.dto.ProductDiscountRespDTO;
import com.altech.electronic.store.model.ProductDiscount;

public interface ProductDiscountService extends BaseService<ProductDiscount>{
	
	ProductDiscountRespDTO addProductDiscount(ProductDiscountReqDTO productDiscountReqDTO);
	
	ProductDiscountRespDTO getByProductId(Long productId);

}
