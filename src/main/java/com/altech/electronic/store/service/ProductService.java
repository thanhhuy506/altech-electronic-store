package com.altech.electronic.store.service;

import com.altech.electronic.store.dto.ProductReqDTO;
import com.altech.electronic.store.dto.ProductRespDTO;
import com.altech.electronic.store.model.Product;

public interface ProductService  extends BaseService<Product>{
	
	ProductRespDTO createProduct(ProductReqDTO productReqDTO);
	
	void validateProduct(Long productId, Integer requestedQuantity);
	
}
