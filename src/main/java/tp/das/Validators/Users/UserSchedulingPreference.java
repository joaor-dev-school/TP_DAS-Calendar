package tp.das.Validators.Users;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserSchedulingPreferenceValidator.class)
public @interface UserSchedulingPreference {
    String message() default "{tp.das.Users.UserSchedulingPreference.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
