package tp.das.DTOs.User;

import tp.das.DTOs.Event.EventDetailsResponseDTO;

import java.util.List;

public class UserResponseDTO {
    private Long id;
    private String name;
    private String username;
    private List<EventDetailsResponseDTO> notifications;

    public UserResponseDTO(Long id, String name, String username) {
        this.id = id;
        this.name = name;
        this.username = username;
    }

    public UserResponseDTO(Long id, String name, String username, List<EventDetailsResponseDTO> notifications) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.notifications = notifications;
    }

    public List<EventDetailsResponseDTO> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<EventDetailsResponseDTO> notifications) {
        this.notifications = notifications;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
