package tp.das.Validators.Users;

import tp.das.Service.UsersService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UsersIdsValidator implements ConstraintValidator<UsersIds, List<Long>> {
    @Override
    public void initialize(UsersIds constraintAnnotation) {
        // Empty by design.
    }

    @Override
    public boolean isValid(List<Long> usersIds, ConstraintValidatorContext constraintValidatorContext) {
        if (usersIds == null) {
            return true;
        }

        for (Long userId : usersIds) {
            if (UsersService.findById(userId) == null) {
                return false;
            }
        }
        return true;
    }
}
