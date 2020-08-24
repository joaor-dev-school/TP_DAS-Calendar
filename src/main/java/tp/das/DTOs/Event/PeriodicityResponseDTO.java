package tp.das.DTOs.Event;

import java.util.List;

public class PeriodicityResponseDTO {
    private List<PeriodicityRuleResponseDTO> rules;
    private Long rangeTimestamp;

    public PeriodicityResponseDTO(List<PeriodicityRuleResponseDTO> rules, Long rangeTimestamp) {
        this.rules = rules;
        this.rangeTimestamp = rangeTimestamp;
    }

    public List<PeriodicityRuleResponseDTO> getRules() {
        return rules;
    }

    public void setRules(List<PeriodicityRuleResponseDTO> rules) {
        this.rules = rules;
    }

    public Long getRangeTimestamp() {
        return rangeTimestamp;
    }

    public void setRangeTimestamp(Long rangeTimestamp) {
        this.rangeTimestamp = rangeTimestamp;
    }
}
