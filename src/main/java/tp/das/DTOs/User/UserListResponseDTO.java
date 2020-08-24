package tp.das.DTOs.User;

import java.util.List;

public class UserListResponseDTO {
    private List<UserResponseDTO> items;

    public UserListResponseDTO(List<UserResponseDTO> items) {
        this.items = items;
    }

    public List<UserResponseDTO> getItems() {
        return items;
    }

    public void setItems(List<UserResponseDTO> items) {
        this.items = items;
    }
}
