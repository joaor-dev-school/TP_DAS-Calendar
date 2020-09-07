package tp.das.DTOs;

public class BooleanStateResponseDTO {
    private Boolean state;

    public BooleanStateResponseDTO(Boolean state) {
        this.state = state;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}
