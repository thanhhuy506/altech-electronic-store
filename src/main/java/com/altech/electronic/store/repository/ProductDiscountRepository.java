package com.altech.electronic.store.repository;

import java.util.Optional;

import com.altech.electronic.store.model.ProductDiscount;

public interface ProductDiscountRepository extends BaseRepository<ProductDiscount>{
	
	Optional<ProductDiscount> findByProductIdAndIsDeletedFalse(Long productId);

}
