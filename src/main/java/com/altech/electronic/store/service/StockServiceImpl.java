package com.altech.electronic.store.service;

import dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altech.electronic.store.model.Stock;
import com.altech.electronic.store.repository.BaseRepository;
import com.altech.electronic.store.repository.ProductRepository;
import com.altech.electronic.store.repository.StockRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class StockServiceImpl extends BaseServiceImpl<Stock> implements StockService {
    
    private final StockRepository stockRepository;
    private final ProductRepository productRepository;
    
    @Override
    public Stock getStockByProductId(Long productId) {
    	return null;
//        return stockRepository.findByProductId(productId)
//                .orElseThrow(() -> new ResourceNotFoundException(
//                    "Stock not found for product id: " + productId));
    }
    
    @Override
    public void reserveStock(Long productId, int quantity) {
        Stock stock = getStockByProductId(productId);
        stock.reserve(quantity);
        stockRepository.save(stock);
    }
    
    @Override
    public void releaseStock(Long productId, int quantity) {
        Stock stock = getStockByProductId(productId);
        stock.release(quantity);
        stockRepository.save(stock);
    }
    
    @Override
    public void commitStock(Long productId, int quantity) {
        Stock stock = getStockByProductId(productId);
        stock.commit(quantity);
        stockRepository.save(stock);
    }
    
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void restock(Long productId, int additionalQuantity) {
        Stock stock = getStockByProductId(productId);
        stock.restock(additionalQuantity);
        stockRepository.save(stock);
    }

	@Override
	public Integer getAvailableQuantity(Long productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getReservedQuantity(Long productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getSoldQuantity(Long productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected BaseRepository<Stock> getRepository() {
		// TODO Auto-generated method stub
		return stockRepository;
	}
    
    // View methods omitted for brevity
}