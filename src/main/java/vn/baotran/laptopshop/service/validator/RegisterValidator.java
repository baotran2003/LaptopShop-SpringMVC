package vn.baotran.laptopshop.service.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Service;
import vn.baotran.laptopshop.domain.dto.RegisterDto;
import vn.baotran.laptopshop.service.impl.UserServiceImpl;

@Service
public class RegisterValidator implements ConstraintValidator<RegisterChecked, RegisterDto> {
    private final UserServiceImpl userService;

    public RegisterValidator(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(RegisterDto user, ConstraintValidatorContext context) {
        boolean valid = true;

        // Check if password fields match
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            context.buildConstraintViolationWithTemplate("Passwords must match")
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            valid = false;
        }

            // Check email
            if(this.userService.checkEmailExists(user.getEmail())) {
                context.buildConstraintViolationWithTemplate("Email already exists")
                        .addPropertyNode("email")
                        .addConstraintViolation()
                        .disableDefaultConstraintViolation();
                valid = false;
            }

        return valid;
    }
}
