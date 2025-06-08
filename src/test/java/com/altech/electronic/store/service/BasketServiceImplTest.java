package com.altech.electronic.store.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
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

import com.altech.electronic.store.dto.BasketItemRespDTO;
import com.altech.electronic.store.dto.BasketRespDTO;
import com.altech.electronic.store.model.Basket;
import com.altech.electronic.store.model.BasketItem;
import com.altech.electronic.store.model.Product;
import com.altech.electronic.store.model.Stock;
import com.altech.electronic.store.repository.BasketRepository;
import com.altech.electronic.store.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class BasketServiceImplTest {

	@Mock
	private BasketRepository basketRepository;

	@Mock
	private ProductRepository productRepository;
	
	@Mock
	private ProductService productService;

	@InjectMocks
	private BasketServiceImpl basketService;

	private Basket basket;
	private BasketItem basketItem;
	private BasketRespDTO basketRespDTO;
	private BasketItemRespDTO basketItemRespDTO;
	private Product product;
	private Stock stock;

	@BeforeEach
	public void setup() {
		basket = new Basket();
		basket.setId(1L);
		basket.setCustomerId(1L);
		basketItem = new BasketItem();
		basketItem.setId(1L);
		basketItem.setBasket(basket);
		basketItemRespDTO = new BasketItemRespDTO();
		basketItemRespDTO.setProductId(1L);
		basketItemRespDTO.setQuantity(1);
		List<BasketItemRespDTO> items = new ArrayList<>();
		items.add(basketItemRespDTO);
		basketRespDTO = new BasketRespDTO();
		basketRespDTO.setId(1L);
		basketRespDTO.setCustomerId(1L);
		basketRespDTO.setItems(items);
		product = new Product();
		product.setId(1L);
		product.setPrice(BigDecimal.valueOf(10));
		stock = new Stock();
		stock.setId(1L);
		stock.setQuantity(100);
		product.setStock(stock);
	}

	@Test
	public void testGetBasket() {
		when(basketRepository.findByCustomerId(any())).thenReturn(Optional.of(basket));
		BasketRespDTO result = basketService.getBasket(1L);
		assertNotNull(result);
		assertEquals(basketRespDTO.getId(), result.getId());
	}

	@Test
	public void testAddItemToBasket() {
		doNothing().when(productService).validateProduct(any(), any());
		when(basketRepository.findByCustomerId(any())).thenReturn(Optional.of(basket));
		when(basketRepository.save(any())).thenReturn(basket);
		BasketRespDTO result = basketService.addToBasket(1L, basketItemRespDTO.getProductId(),
				basketItemRespDTO.getQuantity());
		assertNotNull(result);
		assertEquals(basketRespDTO.getId(), result.getId());
	}

	@Test
	public void testRemoveItemFromBasket() {
		doNothing().when(productService).validateProduct(any(), any());
		when(basketRepository.findByCustomerId(any())).thenReturn(Optional.of(basket));
		when(basketRepository.save(any())).thenReturn(basket);
		BasketRespDTO result = basketService.removeFromBasket(1L, 1L, 1);
		assertNotNull(result);
		assertEquals(basketRespDTO.getId(), result.getId());
	}

	@Test
	public void testClearBasket() {
		when(basketRepository.findByCustomerId(any())).thenReturn(Optional.of(basket));
		when(basketRepository.save(any())).thenReturn(basket);
		basketService.clearBasket(basket.getCustomerId());
	}

}