package tp.das.Validators.Events;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SchedulingPreferenceValidator.class)
public @interface SchedulingPreference {
    String message() default "{tp.das.Events.SchedulingPreference.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
