package com.altech.electronic.store.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altech.electronic.store.exception.InsufficientStockException;
import com.altech.electronic.store.exception.ResourceNotFoundException;
import com.altech.electronic.store.mapper.ProductMapper;
import com.altech.electronic.store.model.Product;
import com.altech.electronic.store.model.Stock;
import com.altech.electronic.store.repository.BaseRepository;
import com.altech.electronic.store.repository.ProductRepository;

import dto.ProductReqDTO;
import dto.ProductRespDTO;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl extends BaseServiceImpl<Product> implements ProductService {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	private final ProductRepository productRepository;
	
	@Override
    public ProductRespDTO createProduct(ProductReqDTO productReqDTO) {
		logger.info("Saving new product: {}", productReqDTO);
        Product product = ProductMapper.toEntity(productReqDTO);
        Stock stock = Stock.builder()
                .product(product)
                .quantity(productReqDTO.getStockQuantity())
                .reservedQuantity(0)
                .soldQuantity(0)
                .build();
        product.setStock(stock);
        Product savedProduct = productRepository.save(product);
        ProductRespDTO productResp = ProductMapper.toDTO(savedProduct);
        logger.info("Product saved successfully: {}", savedProduct);
        return productResp;
    }
	
	@Override
    public void validateProduct(Long productId, Integer requestedQuantity) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found by product id:  ", productId));
            
        if (requestedQuantity != null && product.getStock().getAvailableQuantity() < requestedQuantity) {
            throw new InsufficientStockException("Request product %s not enough with quantity %d in stock ".formatted(productId, requestedQuantity));
        }
    }

	@Override
	protected BaseRepository<Product> getRepository() {
		return productRepository;
	}

}
