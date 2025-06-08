package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.altech.electronic.store.enums.DiscountTypeEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDiscountRespDTO {
	
	private Long id;
    private Long discountId;
    private Long productId;
    private DiscountTypeEnum type;
    private String description;
    private Boolean isActive;
    private LocalDateTime validFrom;
    private LocalDateTime validTo;
    private DiscountDTO discount;
    
}
