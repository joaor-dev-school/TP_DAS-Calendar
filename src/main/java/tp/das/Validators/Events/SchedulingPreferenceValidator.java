package tp.das.Validators.Events;

import tp.das.DTOs.Event.EventSchedulingStatusDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SchedulingPreferenceValidator implements ConstraintValidator<SchedulingPreference, EventSchedulingStatusDTO> {
    @Override
    public void initialize(SchedulingPreference constraintAnnotation) {
        // Empty by design.
    }

    @Override
    public boolean isValid(EventSchedulingStatusDTO eventSchedulingStatusDTO,
                           ConstraintValidatorContext constraintValidatorContext) {
        return !eventSchedulingStatusDTO.getAccept()
                || (eventSchedulingStatusDTO.getPreferredTimestamps() != null
                && eventSchedulingStatusDTO.getAcceptableTimestamps() != null
                && !eventSchedulingStatusDTO.getPreferredTimestamps().isEmpty()
                && !eventSchedulingStatusDTO.getAcceptableTimestamps().isEmpty());
    }
}