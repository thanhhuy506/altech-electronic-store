package dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegistrationDTO {
	String email;
    String password;
    String firstName;
    String lastName;
    String phoneNumber;
    String address;
}
