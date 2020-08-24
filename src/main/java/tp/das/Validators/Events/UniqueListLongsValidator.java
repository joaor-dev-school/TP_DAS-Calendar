package tp.das.Validators.Events;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.stream.Collectors;

public class UniqueListLongsValidator<T extends Annotation> implements ConstraintValidator<T, List<Long>> {

    @Override
    public void initialize(T constraintAnnotation) {

    }

    @Override
    public boolean isValid(List<Long> participants, ConstraintValidatorContext constraintValidatorContext) {
        if (participants == null) {
            return true;
        }

        List<Long> participantsSorted = participants.stream().sorted().collect(Collectors.toList());
        for (int i = 1; i < participantsSorted.size(); i++) {
            if (participantsSorted.get(i).equals(participantsSorted.get(i - 1))) {
                return false;
            }
        }
        return true;
    }
}
