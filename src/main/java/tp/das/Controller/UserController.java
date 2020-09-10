package tp.das.Controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import tp.das.DTOs.Event.EventNotificationResponseDTO;
import tp.das.DTOs.Event.UserOperationDTO;
import tp.das.DTOs.Notification.NotificationChangeStateDTO;
import tp.das.DTOs.User.UserSchedulingPreferencesDTO;
import tp.das.Model.Shared.LongObject;
import tp.das.Service.NotificationsService;
import tp.das.Service.TimerService;
import tp.das.Service.UsersService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping(path = "/all")
    public ResponseEntity getUsers() {
        return ResponseEntity.ok(UsersService.findByAll());
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity getUser(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(UsersService.findById(userId));
    }

    @PutMapping(path = "/calendar_preferences")
    public ResponseEntity changeCalendarPreferences(@Valid @RequestBody UserSchedulingPreferencesDTO preferencesDTO) {
        UsersService.changeUserPreferences(preferencesDTO);
        return ResponseEntity.ok().build();
    }

    /*@GetMapping(path = "/notifications/{userId}")
    public ResponseBodyEmitter getNotifications(@PathVariable("userId") Long userId) {
        final LongObject subscription = new LongObject(-1);
        SseEmitter sseEmitter = new SseEmitter();
        final ExecutorService nonBlockingService = Executors.newSingleThreadExecutor();
        nonBlockingService.execute(() -> {
            try {
                subscription.value = TimerService.getInstance().subscribe((Long timeMs) -> {
                    final List<EventNotificationResponseDTO> notificationsDTO = NotificationsService.getInstance()
                            .getUserNotifications(userId);
                    try {
                        sseEmitter.send(notificationsDTO, MediaType.APPLICATION_JSON);
                    } catch (IOException e) {
                        e.printStackTrace();
                        sseEmitter.completeWithError(e);
                        TimerService.getInstance().unsubscribe(subscription.value);
                    }
                });
            } catch (Exception ex) {
                sseEmitter.completeWithError(ex);
                TimerService.getInstance().unsubscribe(subscription.value);
            }
        });
        sseEmitter.onTimeout(sseEmitter::complete);
        return sseEmitter;
    }*/

    @GetMapping(path = "/notifications/{userId}")
    public ResponseEntity getNotifications(@PathVariable("userId") Long userId) {
        final List<EventNotificationResponseDTO> notificationsDTO = NotificationsService.getInstance()
                .getUserNotifications(userId);
        return ResponseEntity.ok(notificationsDTO);
    }

    @PutMapping(path = "/notifications/state")
    public ResponseEntity getNotifications(@Valid @RequestBody NotificationChangeStateDTO notification) {
        NotificationsService.getInstance().changeNotificationReadState(notification.getUserId(),
                notification.getNotificationId(), notification.getRead());
        return ResponseEntity.ok().build();
    }
}
