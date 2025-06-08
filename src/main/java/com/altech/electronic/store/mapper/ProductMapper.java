package com.altech.electronic.store.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.altech.electronic.store.dto.ProductReqDTO;
import com.altech.electronic.store.dto.ProductRespDTO;
import com.altech.electronic.store.model.Product;

public class ProductMapper {
	
	public static Product toEntity(ProductReqDTO productReqDTO) {
        return Product.builder()
                .name(productReqDTO.getName())
                .description(productReqDTO.getDescription())
                .price(productReqDTO.getPrice())
                .imageUrl(productReqDTO.getImageURL())
                .category(productReqDTO.getCategory())
                .build();
    }
	
	public static ProductRespDTO toDTO(Product product) {
	    return ProductRespDTO.builder()
	            .id(product.getId())
	            .name(product.getName())
	            .description(product.getDescription())
	            .price(product.getPrice())
	            .stockQuantity(product.getStock().getQuantity())
	            .imageURL(product.getImageUrl())
	            .category(product.getCategory())
	            .reservedQuantity(product.getStock().getReservedQuantity())
	            .soldQuantity(product.getStock().getSoldQuantity())
	            .build();
	}
	
	public static List<ProductRespDTO> toDTOs(List<Product> products) {
	    return products.stream()
	            .map(ProductMapper::toDTO)
	            .collect(Collectors.toList());
	}
}
