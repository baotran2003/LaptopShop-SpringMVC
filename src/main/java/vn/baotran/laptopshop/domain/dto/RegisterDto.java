package vn.baotran.laptopshop.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import vn.baotran.laptopshop.service.validator.RegisterChecked;

@Getter
@Setter
@RegisterChecked
public class RegisterDto {
    @Size(min = 3, message = "First name has at least 2 characters")
    private String firstName;
    private String lastName;
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;
    @Size(min = 3, message = "Password must has at least 3 characters")
    private String password;
    private String confirmPassword;
}
