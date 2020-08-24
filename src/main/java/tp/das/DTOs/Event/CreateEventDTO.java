package tp.das.DTOs.Event;

import org.hibernate.validator.constraints.Length;
import tp.das.Validators.Events.UniqueParticipants;
import tp.das.Validators.Users.UserId;
import tp.das.Validators.Users.UsersIds;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class CreateEventDTO {
    @NotNull
    @Length(min = EventDTOConstants.MIN_NAME_LENGTH, max = EventDTOConstants.MAX_NAME_LENGTH)
    private String eventName;

    @NotNull
    @Min(0)
    @UserId
    private Long creatorId;

    @NotNull
    @Size(max = EventDTOConstants.MAX_PARTICIPANTS)
    @UniqueParticipants
    @UsersIds
    private List<Long> participantsIds;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public List<Long> getParticipantsIds() {
        return this.participantsIds;
    }

    public void setParticipantsIds(List<Long> participantsIds) {
        this.participantsIds = participantsIds;
    }
}
