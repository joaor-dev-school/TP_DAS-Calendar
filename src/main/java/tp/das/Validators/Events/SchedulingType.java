package tp.das.Validators.Events;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SchedulingTypeValidator.class)
public @interface SchedulingType {
    String message() default "{tp.das.Events.SchedulingType.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
