package com.altech.electronic.store.service;

import com.altech.electronic.store.model.Product;

import dto.ProductReqDTO;
import dto.ProductRespDTO;

public interface ProductService  extends BaseService<Product>{
	
	ProductRespDTO createProduct(ProductReqDTO productReqDTO);
	
	void validateProduct(Long productId, Integer requestedQuantity);
	
}
