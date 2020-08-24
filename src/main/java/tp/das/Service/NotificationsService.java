package tp.das.Service;

import tp.das.DTOs.Event.EventNotificationResponseDTO;
import tp.das.Model.Database.DataMappers.EventsDataMapper;
import tp.das.Model.Database.DataMappers.NotificationsDataMapper;
import tp.das.Model.Database.DataMappers.UsersDataMapper;
import tp.das.Model.Database.UnitOfWork;
import tp.das.Model.Event.DateModel;
import tp.das.Model.Event.EventModel;
import tp.das.Model.Event.EventParticipantModel;
import tp.das.Model.Notifications.NotificationModel;
import tp.das.Model.Shared.BooleanObject;
import tp.das.Model.Shared.Pair;
import tp.das.Model.Utilizador.UserModel;

import java.util.*;
import java.util.stream.Collectors;

public class NotificationsService {

    private static final long DAY_TIME_MS = 24 * 60 * 60 * 1000;
    private static final int NOTIFICATION_ANTECEDENCE_DAYS = 3;
    private static NotificationsService notificationsService;
    private Map<Long, List<Pair<EventNotificationResponseDTO, Long>>> cachedNotifications;

    private NotificationsService() {
        this.cachedNotifications = new HashMap<>();
    }

    public static NotificationsService getInstance() {
        if (notificationsService != null) {
            return notificationsService;
        }
        return notificationsService = new NotificationsService();
    }

    public void createNotification(EventModel event) {
        for (EventParticipantModel participant : event.getParticipants()) {
            final UserModel user = participant.getUserModel();

            final NotificationModel notificationModel = new NotificationModel(user, event);
            final UnitOfWork unitOfWork = SessionService.getUnitOfWork();
            unitOfWork.registerNew(notificationModel);
            unitOfWork.commit();

            this.cachedNotifications.remove(user.getId());
        }
    }

    public void createNotification(Long eventId) {
        final EventModel event = EventsDataMapper.getInstance().find(eventId);

        if (event == null) {
            throw new RuntimeException("Error creating notification for event with id " + eventId);
        }

        this.createNotification(event);
    }

    public void removeNotification(EventModel event) {
        final Map<Long, EventParticipantModel> participants = new HashMap<>();

        for (EventParticipantModel participant : event.getParticipants()) {
            participants.put(participant.getUserModel().getId(), participant);

        }

        for (NotificationModel notification : NotificationsDataMapper.getInstance().findAll()) {
            final EventParticipantModel participant = participants.get(notification.getUser().getId());

            if (participant == null) {
                continue;
            }

            this.deleteNotificationOnPersistence(notification);
            this.cachedNotifications.remove(participant.getUserModel().getId());
        }
    }

    public void removeNotification(Long eventId) {
        final EventModel event = EventsDataMapper.getInstance().find(eventId);

        if (event == null) {
            throw new RuntimeException("Error creating notification for event with id " + eventId);
        }

        this.removeNotification(event);
    }

    public List<EventNotificationResponseDTO> getUserNotifications(Long userId) {
        List<Pair<EventNotificationResponseDTO, Long>> notificationPairs = this.cachedNotifications.get(userId);

        if (notificationPairs != null) {
            return notificationPairs.stream()
                    .filter((Pair<EventNotificationResponseDTO, Long> pair) -> System.currentTimeMillis() > pair.b)
                    .map((Pair<EventNotificationResponseDTO, Long> pair) -> pair.a)
                    .collect(Collectors.toList());
        }

        final List<NotificationModel> notifications = NotificationsDataMapper.getInstance().findAll();
        final List<EventNotificationResponseDTO> res = new ArrayList<>();
        final Map<Long, DateModel> firstEventDateMap = new HashMap<>();
        final BooleanObject shouldSaveOnCache = new BooleanObject(true);
        final long currentTimeMs = System.currentTimeMillis();

        notificationPairs = notifications.stream()
                .filter((NotificationModel notification) -> {
                    if (!notification.getUser().getId().equals(userId)) {
                        return false;
                    }
                    final EventModel event = notification.getEvent();
                    final DateModel firstDate = event.getDates().stream().reduce((DateModel prev, DateModel cur) ->
                            cur.getTimestamp() < prev.getTimestamp() ? cur : prev).orElse(null);
                    if (firstDate == null) {
                        shouldSaveOnCache.value = false;
                        return false;
                    }
                    if (firstDate.getTimestamp() <= currentTimeMs) {
                        try {
                            shouldSaveOnCache.value = false;
                            this.deleteNotificationOnPersistence(notification);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        return false;
                    }
                    firstEventDateMap.put(event.getId(), firstDate);
                    return true;
                })
                .map((NotificationModel notification) -> {
                            final EventModel event = notification.getEvent();
                            final Long eventId = event.getId();
                            final DateModel firstDate = firstEventDateMap.get(eventId);
                            final EventNotificationResponseDTO notificationResDTO = new EventNotificationResponseDTO()
                                    .setName(event.getName())
                                    .setCreatorName(event.getCreator().getName())
                                    .setRead(notification.getRead())
                                    .setTotalParticipants(event.getParticipants().size())
                                    .setNextDateTimestamp(firstDate.getTimestamp())
                                    .setEventId(eventId);
                            res.add(notificationResDTO);
                            return new Pair<>(notificationResDTO, this.resolveNextNotificationTime(firstDate.getTimestamp()));
                        }
                ).collect(Collectors.toList());

        if (shouldSaveOnCache.value) {
            this.cachedNotifications.put(userId, notificationPairs);
        }
        return res;
    }

    public void changeNotificationReadState(Long notificationId, Boolean read) {
        final NotificationModel notificationModel = NotificationsDataMapper.getInstance().find(notificationId);
        if (notificationModel == null) {
            throw new RuntimeException("Notification not found with the given id");
        }
        notificationModel.setRead(read);
        final UnitOfWork unitOfWork = SessionService.getUnitOfWork();
        unitOfWork.registerDirty(notificationModel);
        unitOfWork.commit();
    }

    private void deleteNotificationOnPersistence(NotificationModel notification) {
        final UnitOfWork unitOfWork = SessionService.getUnitOfWork();
        unitOfWork.registerDeleted(notification);
        unitOfWork.commit();
    }

    private Long resolveNextNotificationTime(Long dateTimeMs) {
        final long currentTimeMs = System.currentTimeMillis();
        long res = dateTimeMs - DAY_TIME_MS * NOTIFICATION_ANTECEDENCE_DAYS;
        for (int i = NOTIFICATION_ANTECEDENCE_DAYS - 1; i > 0; i++) {
            final long possibleRes = dateTimeMs - DAY_TIME_MS * i;
            if (possibleRes < currentTimeMs) {
                return res;
            }
            res = possibleRes;
        }
        return 0L;
    }
}
