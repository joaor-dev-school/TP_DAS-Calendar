package tp.das.Validators.Users;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserIdValidator.class)
public @interface UserId {
    String message() default "{tp.das.Users.InvalidUserId.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
