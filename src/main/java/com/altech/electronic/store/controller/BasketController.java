package com.altech.electronic.store.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altech.electronic.store.service.BasketService;

import dto.BasketItemReqDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/customer/baskets")
@RequiredArgsConstructor
public class BasketController {

	private static final Logger logger = LoggerFactory.getLogger(BasketController.class);

	private final BasketService basketService;

	@GetMapping()
	public ResponseEntity<?> getBasket() {
		logger.info("Get basket");
		try {
			Long customerId = basketService.getCurrentUserId();
			return ResponseEntity.status(HttpStatus.OK).body(basketService.getBasket(customerId));
		} catch (Exception e) {
			logger.error("Failed to create product {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An unexpected error occurred: " + e.getMessage());
		}
	}

	@PostMapping("/addItem")
	public ResponseEntity<?> addItem(@RequestBody BasketItemReqDTO request) {
		logger.info("Add item into basket {}", request);
		try {
			Long customerId = basketService.getCurrentUserId();
			return ResponseEntity.status(HttpStatus.OK)
					.body(basketService.addToBasket(customerId, request.getProductId(), request.getQuantity()));
		} catch (Exception e) {
			logger.error("Failed to add product to basket{}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An unexpected error occurred: " + e.getMessage());
		}

	}

	@PostMapping("/removeItem")
	public ResponseEntity<?> removeItem(@RequestBody BasketItemReqDTO request) {
		logger.info("Remove product {} from basket for customer id {}", request.getProductId());
		Long customerId = basketService.getCurrentUserId();
		try {
			return ResponseEntity.status(HttpStatus.OK)
					.body(basketService.removeFromBasket(customerId, request.getProductId(), request.getQuantity()));
		} catch (Exception e) {
			logger.error("Failed to remove product from basket {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An unexpected error occurred: " + e.getMessage());
		}

	}

	@DeleteMapping()
	public ResponseEntity<?> clearBasket() {
		try {
			Long customerId = basketService.getCurrentUserId();
			basketService.clearBasket(customerId);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			logger.error("Failed to clear products in basket {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An unexpected error occurred: " + e.getMessage());
		}

	}
}
