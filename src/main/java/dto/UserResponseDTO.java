package dto;

import com.altech.electronic.store.enums.RoleEnum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
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
