package com.altech.electronic.store.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altech.electronic.store.dto.BasketItemRespDTO;
import com.altech.electronic.store.dto.BasketRespDTO;
import com.altech.electronic.store.dto.DiscountDTO;
import com.altech.electronic.store.exception.ResourceNotFoundException;
import com.altech.electronic.store.mapper.BasketMapper;
import com.altech.electronic.store.model.Basket;
import com.altech.electronic.store.repository.BaseRepository;
import com.altech.electronic.store.repository.BasketRepository;
import com.altech.electronic.store.utils.CalculatePrice;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BasketServiceImpl extends BaseServiceImpl<Basket> implements BasketService {

	private static final Logger logger = LoggerFactory.getLogger(BasketServiceImpl.class);

	private final BasketRepository basketRepository;

	private final ProductService productService;

	private final ProductDiscountService productDiscountService;

	/**
	 * Get or create a basket for the given customer ID.
	 *
	 * @param customerId the ID of the customer
	 * @return BasketRespDTO containing the basket details
	 */
	@Override
	public BasketRespDTO getOrCreateBasket(Long customerId) {
		logger.info("Get or create basket for customer id {}", customerId);
		return BasketMapper.toDTO(getOrCreateBasketEntity(customerId));
	}

	/**
	 * Add an item to the basket for the given customer ID.
	 *
	 * @param customerId the ID of the customer
	 * @param productId  the ID of the product to add
	 * @param quantity   the quantity to add
	 * @return BasketRespDTO containing the updated basket details
	 */
	@Override
	public BasketRespDTO addToBasket(Long customerId, Long productId, Integer quantity) {
		logger.info("Adding to basket for customer id {}", customerId);
		productService.validateProduct(productId, quantity);

		Basket basket = getOrCreateBasketEntity(customerId);
		productService.validateProduct(productId, quantity);
		basket.addItem(productId, quantity);
		return buildBasketResp(basketRepository.save(basket));
	}

	/**
	 * Remove an item from the basket for the given customer ID.
	 *
	 * @param customerId the ID of the customer
	 * @param productId  the ID of the product to remove
	 * @param quantity   the quantity to remove
	 * @return BasketRespDTO containing the updated basket details
	 */
	@Override
	public BasketRespDTO removeFromBasket(Long customerId, Long productId, Integer quantity) {
		logger.info("Removing basket for customer id {} product id {}", customerId, productId);
		Basket basket = getBasketEntity(customerId);
		productService.validateProduct(productId, null);
		basket.removeItem(productId, quantity);
		return buildBasketResp(basketRepository.save(basket));

	}

	/**
	 * Clear the basket for the given customer ID.
	 *
	 * @param customerId the ID of the customer
	 */
	@Override
	public void clearBasket(Long customerId) {
		logger.info("Clearing basket for customer id {}", customerId);
		Basket basket = getBasketEntity(customerId);
		basket.clear();
		basketRepository.save(basket);
	}

	@Override
	public BasketRespDTO getBasket(Long customerId) {
		logger.info("Getting basket by customer id {}", customerId);
		Optional<Basket> basOptional = basketRepository.findByCustomerId(customerId);
		if (!basOptional.isPresent()) {
			throw new ResourceNotFoundException("Basket not found for customer: ", customerId);
		}
		return buildBasketResp(basOptional.get());
	}

	private Basket getBasketEntity(Long customerId) {
		logger.info("Start getting basket by customer id {}", customerId);
		return basketRepository.findByCustomerId(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Basket not found for customer: ", customerId));
	}

	private Basket getOrCreateBasketEntity(Long customerId) {
		logger.info("Start getting or creating basket for customer id {}", customerId);
		return basketRepository.findByCustomerId(customerId)
				.orElseGet(() -> basketRepository.save(new Basket(customerId)));
	}

	private BasketItemRespDTO calculateItemPrice(BasketItemRespDTO basketItemRespDTO) {
		try {
			DiscountDTO discount = productDiscountService.getByProductId(basketItemRespDTO.getProductId()) == null
					? null
					: productDiscountService.getByProductId(basketItemRespDTO.getProductId()).getDiscount();
			BigDecimal productPrice = productService.getById(basketItemRespDTO.getProductId()).get().getPrice();
			CalculatePrice.calculatePrice(basketItemRespDTO, productPrice, discount);
		} catch (Exception e) {
			logger.error("Error calculating price for item: {}", basketItemRespDTO.getProductId(), e);
		}

		return basketItemRespDTO;
	}

	private List<BasketItemRespDTO> calcutePriceItemList(List<BasketItemRespDTO> items) {
		return items.stream().map(this::calculateItemPrice).collect(Collectors.toList());
	}

	private BasketRespDTO buildBasketResp(Basket basket) {
		BasketRespDTO basketRespDTO = BasketMapper.toDTO(basket);
		basketRespDTO.setItems(calcutePriceItemList(basketRespDTO.getItems()));
		return CalculatePrice.calculateBasketPrice(basketRespDTO);
	}

	@Override
	protected BaseRepository<Basket> getRepository() {
		return basketRepository;
	}

}
