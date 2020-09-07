package tp.das.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import tp.das.DTOs.Event.EventNotificationResponseDTO;
import tp.das.DTOs.Event.UserOperationDTO;
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

@RestController
@RequestMapping("/user")
public class UserController {
    private ExecutorService nonBlockingService = Executors.newCachedThreadPool();

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

    @GetMapping(path = "/notifications", produces = "text/event-stream")
    public SseEmitter getNotifications(@Valid @RequestBody UserOperationDTO operationDTO) {
        SseEmitter emitter = new SseEmitter();
        final LongObject subscription = new LongObject(-1);
        nonBlockingService.execute(() -> {
            try {
                subscription.value = TimerService.getInstance().subscribe((Long timeMs) -> {
                    final List<EventNotificationResponseDTO> notificationsDTO = NotificationsService.getInstance()
                            .getUserNotifications(operationDTO.getUserId());
                    if (!notificationsDTO.isEmpty()) {
                        try {
                            emitter.send(notificationsDTO);
                        } catch (IOException e) {
                            e.printStackTrace();
                            emitter.completeWithError(e);
                        }
                    }
                });
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            } finally {
                if (subscription.value > -1) {
                    TimerService.getInstance().unsubscribe(subscription.value);
                }
            }
        });
        return emitter;
    }
}
