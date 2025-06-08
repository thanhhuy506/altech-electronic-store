package com.altech.electronic.store.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.altech.electronic.store.model.Product;
import com.altech.electronic.store.model.Stock;
import com.altech.electronic.store.repository.ProductRepository;

import dto.ProductReqDTO;
import dto.ProductRespDTO;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private Stock stock;
    private ProductRespDTO productRespDTO;
    private ProductReqDTO productReqDTO;

    @BeforeEach
    public void setup() {
    	product = new Product();
		product.setId(1L);
		product.setPrice(BigDecimal.valueOf(10));
		stock = new Stock();
		stock.setId(1L);
		stock.setQuantity(100);
		product.setStock(stock);
        productRespDTO = new ProductRespDTO();
        productRespDTO.setId(1L);
        productReqDTO = new ProductReqDTO();
        productReqDTO.setPrice(BigDecimal.valueOf(10));
    }

    @Test
    public void testGetProduct() {
        when(productRepository.findByIdAndIsDeletedFalse(any())).thenReturn(Optional.of(product));
        Product result = productService.getById(1L).get();
        assertNotNull(result);
        assertEquals(productRespDTO.getId(), result.getId());
    }

    @Test
    public void testGetAllProducts() {
        List<Product> products = new ArrayList<>();
        products.add(product);
        when(productRepository.findAllByIsDeletedFalse()).thenReturn(products);
        List<Product> result = productService.getAll();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(productRespDTO.getId(), result.get(0).getId());
    }

    @Test
    public void testCreateProduct() {
        when(productRepository.save(any())).thenReturn(product);
        ProductRespDTO result = productService.createProduct(productReqDTO);
        assertNotNull(result);
        assertEquals(productRespDTO.getId(), result.getId());
    }

    @Test
    public void testUpdateProduct() {
        when(productRepository.existsByIdAndIsDeletedFalse(any())).thenReturn(true);
        when(productRepository.save(any())).thenReturn(product);
        Product result = productService.update(1L, product);
        assertNotNull(result);
        assertEquals(productRespDTO.getId(), result.getId());
    }

    @Test
    public void testDeleteProduct() {
        when(productRepository.findById(any())).thenReturn(Optional.of(product));
        productService.delete(1L);
    }
    
}
