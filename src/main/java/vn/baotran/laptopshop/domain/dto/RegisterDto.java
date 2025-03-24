package vn.baotran.laptopshop.domain.dto;

import lombok.Getter;
import lombok.Setter;
import vn.baotran.laptopshop.service.validator.RegisterChecked;

@Getter
@Setter
@RegisterChecked
public class RegisterDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
}
