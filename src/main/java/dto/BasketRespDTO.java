package dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasketRespDTO {
	
	private Long id;
	private Long customerId;
	
	private List<BasketItemRespDTO> items;
	
	private BigDecimal totalPrice;
	private BigDecimal totalDiscountPrice;
	private BigDecimal finalPrice;

}
