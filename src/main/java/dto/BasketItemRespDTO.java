package dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasketItemRespDTO {
	
	private Long productId;
	private Integer quantity;
	private BigDecimal totalPrice;
	private BigDecimal totalDiscountPrice;
	private BigDecimal finalPrice;

}
