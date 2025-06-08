package com.altech.electronic.store.mapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.altech.electronic.store.dto.BasketItemRespDTO;
import com.altech.electronic.store.dto.BasketRespDTO;
import com.altech.electronic.store.model.Basket;

public class BasketMapper {
	
	
	public static BasketRespDTO toDTO(Basket basket) {
	    return BasketRespDTO.builder()
	            .id(basket.getId())
	            .customerId(basket.getCustomerId())
	            .items(BasketMapper.toItemDTOs(basket.getItems()))
	            .build();
	}
	
	
	public static List<BasketItemRespDTO> toItemDTOs(Map<Long, Integer> items) {
		return items.entrySet()
	            .stream()
	            .map(entry -> BasketItemRespDTO.builder()
	                    .productId(entry.getKey())
	                    .quantity(entry.getValue())
	                    .build())
	            .collect(Collectors.toList());
	}

}
