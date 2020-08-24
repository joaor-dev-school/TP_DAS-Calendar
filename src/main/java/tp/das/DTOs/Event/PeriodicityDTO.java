package tp.das.DTOs.Event;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class PeriodicityDTO {
    @NotNull
    @Size(max = EventDTOConstants.MAX_RULES_LENGTH)
    private List<PeriodicityRuleDTO> rules;

    @NotNull
    @Min(0)
    private Long rangeTimestamp;

    public List<PeriodicityRuleDTO> getRules() {
        return rules;
    }

    public void setRules(List<PeriodicityRuleDTO> rules) {
        this.rules = rules;
    }

    public Long getRangeTimestamp() {
        return rangeTimestamp;
    }

    public void setRangeTimestamp(Long rangeTimestamp) {
        this.rangeTimestamp = rangeTimestamp;
    }
}
