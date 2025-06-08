package com.altech.electronic.store.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altech.electronic.store.exception.ResourceNotFoundException;
import com.altech.electronic.store.mapper.ProductDiscounttMapper;
import com.altech.electronic.store.model.Discount;
import com.altech.electronic.store.model.Product;
import com.altech.electronic.store.model.ProductDiscount;
import com.altech.electronic.store.repository.BaseRepository;
import com.altech.electronic.store.repository.DiscountRepository;
import com.altech.electronic.store.repository.ProductDiscountRepository;
import com.altech.electronic.store.repository.ProductRepository;

import dto.ProductDiscountReqDTO;
import dto.ProductDiscountRespDTO;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductDiscountServiceImpl extends BaseServiceImpl<ProductDiscount> implements ProductDiscountService {

    private static final Logger logger = LoggerFactory.getLogger(ProductDiscountServiceImpl.class);

    private final ProductDiscountRepository productDiscountRepository;

    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;

    @Override
    public ProductDiscountRespDTO addProductDiscount(ProductDiscountReqDTO productDiscountReqDTO) {
        logger.info("Saving product discount with id: {} product id {}", productDiscountReqDTO.getDiscountId(),
                productDiscountReqDTO.getProductId());

        Optional<Discount> discountOptional = discountRepository.findById(productDiscountReqDTO.getDiscountId());
        Optional<Product> productOptional = productRepository.findById(productDiscountReqDTO.getProductId());

        if (discountOptional.isPresent() && productOptional.isPresent()) {
            Discount discount = discountOptional.get();
            Product product = productOptional.get();
            ProductDiscount productDiscount = ProductDiscounttMapper.toEntity(productDiscountReqDTO);
            productDiscount.setDiscount(discount);
            productDiscount.setProduct(product);
            Optional<ProductDiscount> productDiscountOpt = productDiscountRepository
                    .findByProductIdAndIsDeletedFalse(productDiscountReqDTO.getProductId());
            if (productDiscountOpt.isPresent()) {
                ProductDiscount existedProductDiscount = productDiscountOpt.get();
                productDiscount.setId(existedProductDiscount.getId());
            }
            ProductDiscount savedProductDiscount = productDiscountRepository.save(productDiscount);
            return ProductDiscounttMapper.toDTO(savedProductDiscount);
        } else {
            logger.error("Discount or product not found");
            throw new ResourceNotFoundException("Discount or product not found");
        }
    }

    @Override
    public ProductDiscountRespDTO getByProductId(Long productId) {
        Optional<ProductDiscount> productDiscountOpt = productDiscountRepository
                .findByProductIdAndIsDeletedFalse(productId);
        if (productDiscountOpt.isPresent()) {
            return ProductDiscounttMapper.toDTO(productDiscountOpt.get());
        } else {
            logger.error("Product discount not found");
            return null;
        }
    }

    @Override
    protected BaseRepository<ProductDiscount> getRepository() {
        return productDiscountRepository;
    }

}
