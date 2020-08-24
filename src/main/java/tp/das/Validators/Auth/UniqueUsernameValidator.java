package tp.das.Validators.Auth;

import tp.das.Service.AuthService;
import tp.das.Service.UsersService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
        // Empty by design.
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        try {
            return username != null && AuthService.findByUsername(username) == null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
