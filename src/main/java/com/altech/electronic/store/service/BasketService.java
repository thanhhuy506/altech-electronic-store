package com.altech.electronic.store.service;

import com.altech.electronic.store.dto.BasketRespDTO;
import com.altech.electronic.store.model.Basket;

public interface BasketService extends BaseService<Basket>{

	BasketRespDTO getOrCreateBasket(Long customerId);
	
	BasketRespDTO addToBasket(Long customerId, Long productId, Integer quantity);
	
	BasketRespDTO removeFromBasket(Long customerId, Long productId, Integer quantity);

	void clearBasket(Long customerId);
	
	BasketRespDTO getBasket(Long customerId);
}
