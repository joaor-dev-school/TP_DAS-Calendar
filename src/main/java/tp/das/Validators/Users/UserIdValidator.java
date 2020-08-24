package tp.das.Validators.Users;

import tp.das.Service.UsersService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserIdValidator implements ConstraintValidator<UserId, Long> {
    @Override
    public void initialize(UserId constraintAnnotation) {
        // Empty by design.
    }

    @Override
    public boolean isValid(Long userId, ConstraintValidatorContext constraintValidatorContext) {
        return userId != null && UsersService.findById(userId) != null;
    }
}
