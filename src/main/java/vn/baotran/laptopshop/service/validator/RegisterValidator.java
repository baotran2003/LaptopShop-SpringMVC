package vn.baotran.laptopshop.service.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import vn.baotran.laptopshop.domain.dto.RegisterDto;

public class RegisterValidator implements ConstraintValidator<RegisterChecked, RegisterDto> {

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

        // Additional validations can be added here

        return valid;
    }
}
