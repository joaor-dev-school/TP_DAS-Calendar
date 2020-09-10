package tp.das.DTOs.Notification;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class NotificationChangeStateDTO {
    @NotNull
    @Min(0)
    private Long userId;

    @NotNull
    @Min(0)
    private Long notificationId;

    @NotNull
    private Boolean read;

    public NotificationChangeStateDTO(@NotNull @Min(0) Long userId, @NotNull @Min(0) Long notificationId, @NotNull Boolean read) {
        this.userId = userId;
        this.notificationId = notificationId;
        this.read = read;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }
}
