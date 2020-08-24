package tp.das.Validators.Events;

import tp.das.Model.Event.EventTypesEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SchedulingTypeValidator implements ConstraintValidator<SchedulingType, EventTypesEnum> {

    @Override
    public void initialize(SchedulingType constraintAnnotation) {
        // Empty by design.
    }

    @Override
    public boolean isValid(EventTypesEnum type, ConstraintValidatorContext constraintValidatorContext) {
        return type == EventTypesEnum.SCHEDULING_AUTOMATIC || type == EventTypesEnum.SCHEDULING_COLLABORATIVE;
    }
}
