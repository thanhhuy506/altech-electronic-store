package com.altech.electronic.store.controller;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altech.electronic.store.mapper.ProductDiscounttMapper;
import com.altech.electronic.store.model.ProductDiscount;
import com.altech.electronic.store.service.ProductDiscountService;

import dto.ProductDiscountReqDTO;
import dto.ProductDiscountRespDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class ProductDiscountController {

	private static final Logger logger = LoggerFactory.getLogger(ProductDiscountController.class);

	private final ProductDiscountService productDiscountService;

	@PostMapping("/product-discounts")
	public ResponseEntity<?> addProductDiscount(@RequestBody ProductDiscountReqDTO productDiscountReqDTO) {
		logger.info("Saving new product discount: {}", productDiscountReqDTO);
		try {
			ProductDiscountRespDTO product = productDiscountService.addProductDiscount(productDiscountReqDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(product);
		} catch (Exception e) {
			logger.error("Failed to add product discount {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An unexpected error occurred: " + e.getMessage());
		}

	}

	@GetMapping("/product-discounts/{productId}")
	public ResponseEntity<?> getProductById(@PathVariable(required = false, value = "productId") Long productId) {
		logger.info("Getting product by id: {}", productId);
		try {
			ProductDiscountRespDTO productDiscountRespDTO = productDiscountService.getByProductId(productId);
			if (productDiscountRespDTO == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product discount not found");
			}
			return ResponseEntity.status(HttpStatus.OK).body(productDiscountRespDTO);
		} catch (Exception e) {
			logger.error("Failed to get product discount {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An unexpected error occurred: " + e.getMessage());
		}

	}

	@GetMapping("/product-discounts")
	public ResponseEntity<?> getAllProducts() {
		logger.info("Getting all products");
		try {
			List<ProductDiscount> productDiscounts = productDiscountService.getAll();
			if (!productDiscounts.isEmpty()) {
				return ResponseEntity.status(HttpStatus.OK).body(ProductDiscounttMapper.toDTOs(productDiscounts));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.EMPTY_LIST);
			}
		} catch (Exception e) {
			logger.error("Failed to get all product discounts {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An unexpected error occurred: " + e.getMessage());
		}

	}

	@DeleteMapping("/product-discounts/{id}")
	public ResponseEntity<?> deleteProductDiscountById(@PathVariable(required = false, value = "id") Long id) {
		logger.info("Deleting product discount by id: {}", id);
		try {
			productDiscountService.delete(id);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} catch (Exception e) {
			logger.error("Failed to delete product discounts {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An unexpected error occurred: " + e.getMessage());
		}

	}

}
