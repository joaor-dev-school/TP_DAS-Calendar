package tp.das.DTOs.Auth;

import tp.das.DTOs.Event.EventDetailsResponseDTO;
import tp.das.DTOs.User.UserResponseDTO;

import java.util.List;

public class LoginResponseDTO {
    private UserResponseDTO user;
    private List<EventDetailsResponseDTO> notifications;

    public LoginResponseDTO(UserResponseDTO user, List<EventDetailsResponseDTO> notifications) {
        this.user = user;
        this.notifications = notifications;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }

    public List<EventDetailsResponseDTO> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<EventDetailsResponseDTO> notifications) {
        this.notifications = notifications;
    }
}
