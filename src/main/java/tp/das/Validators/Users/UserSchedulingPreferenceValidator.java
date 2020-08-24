package tp.das.Validators.Users;

import tp.das.DTOs.User.UserSchedulingPreferenceDTO;
import tp.das.Model.Utilizador.UserSchedulingPreferenceTypeEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Calendar;

public class UserSchedulingPreferenceValidator implements ConstraintValidator<UserSchedulingPreference, UserSchedulingPreferenceDTO> {
    @Override
    public void initialize(UserSchedulingPreference constraintAnnotation) {
        // Empty by design.
    }

    @Override
    public boolean isValid(UserSchedulingPreferenceDTO userSchedulingStatusDTO,
                           ConstraintValidatorContext constraintValidatorContext) {
        final Long fromTimestamp = userSchedulingStatusDTO.getFromTimestamp();
        final Long toTimestamp = userSchedulingStatusDTO.getToTimestamp();
        if (userSchedulingStatusDTO.getType() != UserSchedulingPreferenceTypeEnum.WEEKEND
                && (fromTimestamp == null || toTimestamp == null)) {
            return false;
        }
        final Calendar fromC = Calendar.getInstance();
        final Calendar toC = Calendar.getInstance();
        fromC.setTimeInMillis(fromTimestamp);
        toC.setTimeInMillis(toTimestamp);
        switch (userSchedulingStatusDTO.getType()) {
            case DAY: {
                this.resolveDayPrecision0(fromC);
                this.resolveDayPrecision0(toC);
                return fromC.getTimeInMillis() == toC.getTimeInMillis();
            }
            case WEEK: {
                final int fromDayWeek = fromC.get(Calendar.DAY_OF_WEEK);
                final int toDayWeek = toC.get(Calendar.DAY_OF_WEEK);
                if (toDayWeek < fromDayWeek) {
                    return false;
                }
                final int daysLeftTillEnd = 7 - fromDayWeek;
                fromC.add(Calendar.DATE, daysLeftTillEnd + 1);
                this.resolveDayPrecision0(fromC);
                return fromC.getTimeInMillis() > toTimestamp;
            }
            case MONTH: {
                return fromC.get(Calendar.WEEK_OF_MONTH) <= toC.get(Calendar.WEEK_OF_MONTH)
                        && fromC.get(Calendar.MONTH) == toC.get(Calendar.MONTH)
                        && fromC.get(Calendar.YEAR) == toC.get(Calendar.YEAR);
            }
            case YEAR: {
                return fromC.get(Calendar.MONTH) <= toC.get(Calendar.MONTH)
                        && fromC.get(Calendar.YEAR) == toC.get(Calendar.YEAR);
            }
            case WEEKEND: {
                return true;
            }
            default:
                return false;
        }
    }

    private void resolveDayPrecision0(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }
}
