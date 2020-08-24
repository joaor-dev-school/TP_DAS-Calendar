package tp.das.DTOs.Event;

import java.util.List;

public class EventListResponseDTO {
    private List<EventListItemResponseDTO> items;

    public EventListResponseDTO(List<EventListItemResponseDTO> items) {
        this.items = items;
    }

    public List<EventListItemResponseDTO> getItems() {
        return items;
    }

    public void setItems(List<EventListItemResponseDTO> items) {
        this.items = items;
    }
}
