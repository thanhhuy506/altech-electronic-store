package dto;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscountDTO {
	
	private Integer requiredQuantity;
    private Integer discountedQuantity;
    private BigDecimal discountValue;

}
