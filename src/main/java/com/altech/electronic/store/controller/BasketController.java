package com.altech.electronic.store.controller;

import static com.altech.electronic.store.constant.PathConstant.CUSTOMER;

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
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(CUSTOMER)
@RequiredArgsConstructor
public class BasketController {

	private static final Logger logger = LoggerFactory.getLogger(BasketController.class);

	private final BasketService basketService;

	@Operation(summary = "Get Basket", description = "Retrieve the current basket")
	@GetMapping("/baskets")
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

	/**
	 * Adds an item to the basket.
	 *
	 * @param request The {@link BasketItemReqDTO} containing the product to add and
	 *                the quantity.
	 * @return The {@link ResponseEntity} containing the updated basket.
	 */
	@Operation(summary = "Add Item to Basket", description = "Add a new item to the basket")
	@PostMapping("/baskets/addItem")
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

	@Operation(summary = "Remove Item from Basket", description = "Remove an item from the basket")
	@PostMapping("/baskets/removeItem")
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

	@Operation(summary = "Clear Basket", description = "Clear all items from the basket")
	@DeleteMapping("/baskets")
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
