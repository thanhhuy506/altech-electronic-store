package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDiscountReqDTO {

    private Long discountId;
    private Long productId;
    private Boolean isActive;
    private LocalDateTime validFrom;
    private LocalDateTime validTo;
    
}
