package com.altech.electronic.store.controller;

import static com.altech.electronic.store.constant.PathConstant.ADMIN;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

import com.altech.electronic.store.mapper.ProductMapper;
import com.altech.electronic.store.model.Product;
import com.altech.electronic.store.service.ProductService;

import dto.ProductReqDTO;
import dto.ProductRespDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ADMIN)
@RequiredArgsConstructor
public class ProductController {

	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	private final ProductService productService;

	@Operation(summary = "Create Product", description = "Creates a new product", tags = "product")
	@PostMapping("/products")
	public ResponseEntity<?> createProduct(@RequestBody ProductReqDTO productReqDTO) {
		logger.info("Saving new product: {}", productReqDTO);
		try {
			ProductRespDTO product = productService.createProduct(productReqDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(product);
		} catch (Exception e) {
			logger.error("Failed to create product {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An unexpected error occurred: " + e.getMessage());
		}
	}

	@Operation(summary = "Get Product by Id", description = "Retrieves a product", tags = "product")
	@GetMapping("/products/{id}")
	public ResponseEntity<?> getProductById(@PathVariable(required = false, value = "id") Long id) {
		logger.info("Getting product by id: {}", id);
		try {
			Optional<Product> product = productService.getById(id);
			if (product.isPresent()) {
				return ResponseEntity.status(HttpStatus.OK).body(ProductMapper.toDTO(product.get()));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		} catch (Exception e) {
			logger.error("Failed to get product by id {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An unexpected error occurred: " + e.getMessage());
		}

	}

	@Operation(summary = "Get All Products", description = "Retrieves all products", tags = "product")
	@GetMapping("/products")
	public ResponseEntity<?> getAllProducts() {
		logger.info("Getting all products");
		try {
			List<Product> products = productService.getAll();
			if (!products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.OK).body(ProductMapper.toDTOs(products));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.EMPTY_LIST);
			}
		} catch (Exception e) {
			logger.error("Failed to get all product {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An unexpected error occurred: " + e.getMessage());
		}

	}

	@Operation(summary = "Delete Product", description = "Deletes a product by ID", tags = "product")
	@DeleteMapping("/products/{id}")
	public ResponseEntity<?> deleteProductById(@PathVariable(required = false, value = "id") Long id) {
		logger.info("Deleting product by id: {}", id);
		try {
			productService.delete(id);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} catch (Exception e) {
			logger.error("Failed to delete product {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An unexpected error occurred: " + e.getMessage());
		}
	}

}
