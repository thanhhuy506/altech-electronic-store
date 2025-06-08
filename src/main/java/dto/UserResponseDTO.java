package dto;

import java.math.BigDecimal;

import com.altech.electronic.store.enums.RoleEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

	Long id;
    String email;
    RoleEnum role;
    String firstName;
    String lastName;
    String phoneNumber;
    String address;
    String token;
    
}
