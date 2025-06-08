package com.altech.electronic.store.repository;

import java.util.Optional;

import com.altech.electronic.store.model.Basket;

public interface BasketRepository extends BaseRepository<Basket> {
	
	Optional<Basket> findByCustomerId(Long customerId);
}
